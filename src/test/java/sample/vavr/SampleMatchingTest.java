package sample.vavr;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleMatchingTest {

    @Test
    public void testOk() {
        val either = new SampleMatching();
        val result = either.devide(1, 2);
        assertThat(result).isEqualTo("Ok");
    }

    @Test
    public void testFail() {
        val either = new SampleMatching();
        val result = either.devide(1, 0);
        assertThat(result).isEqualTo("Fail");
    }

    @Test
    public void testMatchingPattern(){
        val m = new SampleMatching();
        assertThat(m.test("o")).isEqualTo("Yes is Other");
        assertThat(m.test(2)).isEqualTo("Yes is 2");
    }

}
