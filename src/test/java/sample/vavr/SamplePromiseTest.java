package sample.vavr;

import io.vavr.concurrent.Promise;
import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SamplePromiseTest {

    @Test
    public void testPromise() {
        val _promise = new SamplePromise();
        val result = _promise.devide(2, 1);
        result.future().onSuccess((a) -> {
            assertThat(a).isEqualTo(2);
        });

    }

    @Test
    public void makePromise() {
        val p = Promise.make();

        val pSuccess = p.success(2);

        pSuccess.future().onSuccess((a) -> assertThat(a).isEqualTo(2));
    }


}
