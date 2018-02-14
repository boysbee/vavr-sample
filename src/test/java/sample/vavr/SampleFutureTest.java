package sample.vavr;

import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleFutureTest {

    @Test
    public void testOnSuccess() {
        val future = new SampleFuture();
        val result = future.divide(2, 1);
        result.onSuccess((a) -> assertThat(a).isEqualTo(2));
    }

    @Test
    public void testOnFailure() {
        val future = new SampleFuture();
        val result = future.divide(2, 0);
        result.onFailure(e -> assertThat(e).isInstanceOf(ArithmeticException.class));
    }

    @Test
    public void testOnComplete() {
        val future = new SampleFuture();
        val result = future.divide(2, 1);
        result.onComplete(t -> t.onSuccess(a -> assertThat(a).isEqualTo(2)));
    }


}
