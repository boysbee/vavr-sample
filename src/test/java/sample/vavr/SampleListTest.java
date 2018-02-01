package sample.vavr;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleListTest {

    @Test
    public void testTuple() {
        val t = new SampleList().makeList(1,2,3,4);
        assertThat(t.sum()).isEqualTo(10L);
    }



}
