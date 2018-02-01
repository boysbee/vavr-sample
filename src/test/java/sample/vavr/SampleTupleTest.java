package sample.vavr;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleTupleTest {

    @Test
    public void testTuple() {
        val t = new SampleTuple().makeTuple();
        assertThat(t._1).isEqualTo("Good");
        assertThat(t._2).isEqualTo(1);
    }

    @Test
    public void testTupleMap() {
        val t = new SampleTuple().makeTuple().map(i -> i.toUpperCase(), j -> j + 2);
        assertThat(t._1).isEqualTo("GOOD");
        assertThat(t._2).isEqualTo(3);
    }


}
