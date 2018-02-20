package sample.vavr;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleTryTest {

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

}