package sample.vavr;

import io.vavr.API;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.val;
import org.junit.Test;

import java.util.Optional;

import static io.vavr.API.*;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.Predicates.is;
import static io.vavr.control.Option.none;
import static org.assertj.core.api.Assertions.assertThat;

public class SampleOptionTest {

    @Test
    public void testOption() {
        val t = new SampleOption().makeOption(1);
        val r = t.map((a) -> a + 2).getOrNull();
        assertThat(r).isEqualTo(3);
    }

    @Test
    public void testOptionNullValue() {
        val t = new SampleOption().makeOption(null);
        val r = t.flatMap(a -> none());
        assertThat(r).isEqualTo(none());
    }

    @Test
    public void testOptionToTry() {
        val t = Option.some(null);
        System.out.println(t.map(b -> b).get());
    }

    @Test
    public void testPeek() {
        val result = Option.some("Ok").peek(e -> System.out.println("message is " + e)).get();
        assertThat(result).isEqualTo("Ok");
    }

    @Test
    public void testPeekOptional() {
        val e = Optional.of("Ok");
        val c = e.map(m -> {
            System.out.println("Message is " + m);
            return m;
        }).get();
        assertThat(c).isEqualTo("Ok");

        Optional<String> b = Optional.ofNullable(null);
        String f = b.orElseGet(() -> "Other");
        System.out.println(f);

    }


    @Test
    public void ofNull() {
        val a = Option.of(null);
        assertThat(a.isDefined()).isFalse();
        val r = Match(a).of(
                Case($Some($()), "Some val"),
                Case($None(), "None")

        );
        assertThat(r).isEqualTo("None");
    }

    @Test
    public void someNull() {
        val a = Option.some(null);
        val r = Match(a).of(
                Case($Some($()), "Some val"),
                Case($None(), "None")

        );
        assertThat(r).isEqualTo("Some val");
    }

    @Test
    public void testForComprehension() {
        val introduce = Option.some("My name is");
        val name = Option.some("Notto");
        val job = Option.some("My job is Programmer");
        val r = introduce.flatMap(a -> name.flatMap(b -> job.flatMap(c -> Option.of(a + " " + b + " " + c)))).get();
        assertThat(r).isEqualTo("My name is Notto My job is Programmer");
    }


}
