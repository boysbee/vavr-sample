package sample.vavr;

import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Function4;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleCurryingTest {

    @Test
    public void testCurried() {
        Function3<Integer, Integer, Integer, String> f1 = (a, b, c) -> {
            return String.valueOf(a + b + c);
        };
        Function1<Integer, Function1<Integer, String>> f2 = f1.curried().apply(1);
        Function1<Integer, String> f3 = f2.apply(2);
        String f4 = f3.apply(3);
        assertThat(f4).isEqualTo("6");
    }

    @Test
    public void testCurried_withFunction() {
        Function3<Integer, Integer, Function<Integer, String>, String> f1 = (a, b, c) -> {
            return c.apply(a + b);
        };
        Function<Integer, String> f4 = String::valueOf;
        Function1<Integer, Function1<Function<Integer, String>, String>> f2 = f1.curried().apply(1);
        Function1<Function<Integer, String>, String> f3 = f2.apply(2);
        String actual = f3.apply(f4);
        assertThat(actual).isEqualTo("3");
    }

    @Test
    public void testCurried_withFunction3() {
        Function4<Integer, Integer, Integer, Function3<Integer, Integer, Integer, String>, String> f1 = (a, b, c, f) -> {
            return f.apply(a, b, c);
        };
        Function1<Integer, Function1<Integer, Function1<Function3<Integer, Integer, Integer, String>, String>>> f2 = f1.curried().apply(1);
        Function1<Integer, Function1<Function3<Integer, Integer, Integer, String>, String>> f3 = f2.apply(2);
        Function1<Function3<Integer, Integer, Integer, String>, String> f4 = f3.apply(3);
        Function3<Integer, Integer, Integer, String> f = (a, b, c) -> String.valueOf(a + b + c);
        String actual = f4.apply(f);
        assertThat(actual).isEqualTo("6");
    }

}
