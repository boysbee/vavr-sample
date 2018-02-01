package sample.vavr;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleEitherTest {

    @Test
    public void testEitherRight() {
        val either = new SampleEither();
        val result = either.devide(1, 2);
        assertThat(result.isRight()).isTrue();
    }

    @Test
    public void testEitherLeft() {
        val either = new SampleEither();
        val result = either.devide(1, 0);
        assertThat(result.isLeft()).isTrue();
    }
}
