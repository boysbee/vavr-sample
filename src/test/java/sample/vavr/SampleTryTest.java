package sample.vavr;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import sample.BackendService;

import java.io.IOException;
import java.net.SocketException;
import java.util.function.Supplier;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
public class SampleTryTest {
    BackendService backendService;

    @Test
    public void tryMapFailure() {
        Try<String> a = Try.of(() -> {
            throw new Exception("");
        });
        Match.Case<Exception, Exception> caseA = Case($(instanceOf(Exception.class)), new AException("Exception"));
        Match.Case<IOException, Exception> caseB = Case($(instanceOf(IOException.class)), new BException("IO Exception"));
        Match.Case<SocketException, Exception> caseC = Case($(instanceOf(SocketException.class)), new BException("SocketException"));
        val b = a.mapFailure(caseA, caseB, caseC).getCause();
        assertThat(b).isInstanceOf(IOException.class);
        assertThat(b.getMessage()).isEqualTo("Exception");
    }

    @Test
    public void tryPlus2Integer() {
        val sample = new SampleTry();
        sample.plus(1, 2)
                .onSuccess(System.out::println);
    }

    @Test
    public void tryDivideOnSuccessWhenDivisorNotZero() {
        val sample = new SampleTry();
        sample.divide(1, 1)
                .onSuccess(System.out::println);
    }

    @Test
    public void tryDivideOnFailureWhenDivisorIsZero() {
        val sample = new SampleTry();
        sample.divide(1, 0)
                .onFailure(e -> System.out.println(e.getMessage()));
    }

    @Test
    public void tryWithOption() {
        val tryOption = Try.of(() -> Option.some("Ok"));
        val result = tryOption.peek(System.out::print).map(a -> "You may be " + a.get()).get();
        assertThat(result).isEqualTo("You may be Ok");
    }

    @Test
    public void tryTransform() {
        val a = Try.failure(new Exception(""));
        val result = a.onFailure(e -> System.out.println(e)).getOrElse("Fail");
        assertThat(result).isEqualTo("Fail");
    }

    @Before
    public void setUp() {
        backendService = mock(BackendService.class);
    }

    @Test
    public void tryFailureWithGetOrElse() {
        val a = Try.of(() -> {
            throw new Exception("");
        }).getOrElse("Failure");
        assertThat(a).isEqualTo("Failure");
    }




    @Test
    public void tryWithCircuitBreakerAndRetry() {

        when(backendService.doSomething()).thenReturn("Ok");
// Create a CircuitBreaker (use default configuration)
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");
// Create a Retry with at most 3 retries and a fixed time interval between retries of 500ms
        Retry retry = Retry.ofDefaults("backendName");

// Decorate your call to BackendService.doSomething() with a CircuitBreaker
        Supplier decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, backendService::doSomething);

// Decorate your call with automatic retry
        decoratedSupplier = Retry
                .decorateSupplier(retry, decoratedSupplier);

// Execute the decorated supplier and recover from any exception
        String result = (String) Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();

// When you don't want to decorate your lambda expression,
// but just execute it and protect the call by a CircuitBreaker.
        String actual = circuitBreaker.executeSupplier(backendService::doSomething);
        assertThat(actual).isEqualTo("Ok");

    }

    @Test
    public void tryWithRetryException() throws IOException {
        // Given the HelloWorldService throws an exception
        BDDMockito.given(backendService.doSomethingWithException()).willThrow(new IOException("BAM!"));

        // Create a Retry with a backoff function doubling the interval
        RetryConfig config = RetryConfig.custom().intervalFunction(IntervalFunction.ofExponentialBackoff(500, 2.0)).build();
        Retry retry = Retry.of("id", config);
        // Decorate the invocation of the HelloWorldService
        CheckedFunction0<String> retryableSupplier = Retry.decorateCheckedSupplier(retry, backendService::doSomethingWithException);

        // When
        Try<String> result = Try.of(retryableSupplier).recover(t -> "From recovery");

        verify(backendService, times(3)).doSomethingWithException();
        assertThat(result.get()).isEqualTo("From recovery");
    }

    static class AException extends Exception {

        public AException(String exception) {
            super(exception);
        }
    }
    static class BException extends Exception {
        public BException(String exception) {
            super(exception);
        }
    }
    static class CException extends Exception {
        public CException(String exception) {
            super(exception);
        }
    }
}
