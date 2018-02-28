package sample.vavr;

import io.vavr.control.Option;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OptionalVsOptionTest {

    @Test
    public void test_optional_vs_optinal() {

        // Optional<String> valOfOptinalOfIsNull = Optional.of(null); // this should throw null pointer exception!!

        Optional<String> valOfOptinalOfIsNotNull = Optional.of("NotNull");
        assertThat(valOfOptinalOfIsNotNull.isPresent()).isTrue();
        assertThat(valOfOptinalOfIsNotNull.get()).isEqualTo("NotNull");
        valOfOptinalOfIsNotNull.ifPresent(System.out::println); // if present value should show that val.


        Optional<String> valOfOptinalOfNullableIsNull = Optional.ofNullable(null); // this not throw null pointer exception.
        assertThat(valOfOptinalOfNullableIsNull.isPresent()).isFalse();
        assertThat(valOfOptinalOfNullableIsNull.orElse("Other value")).isEqualTo("Other value");
        assertThatThrownBy(() -> valOfOptinalOfNullableIsNull.orElseThrow(MyNullPointerException::new))
                .isInstanceOf(MyNullPointerException.class);

        Optional<String> valOfOptinalOfNullableIsNotNull = Optional.ofNullable("NotNullable");
        assertThat(valOfOptinalOfNullableIsNotNull.isPresent()).isTrue();
        assertThat(valOfOptinalOfNullableIsNotNull.orElse("Other value")).isEqualTo("NotNullable");


        Option<String> valOfOptinOfIsNotNull = Option.of("NotNull");
        assertThat(valOfOptinOfIsNotNull.isDefined()).isTrue();
        assertThat(valOfOptinOfIsNotNull.isEmpty()).isFalse();
        assertThat(valOfOptinOfIsNotNull.get()).isEqualTo("NotNull");
        Option<String> afterValOfOptinOfIsNotNullPeek = valOfOptinOfIsNotNull.peek(System.out::println)
                .map(e -> String.format("This is value after peek %s", e)); // Same like `Optional.ifPresent(function consumer)` but you can use map/get/getOrElse/getOrElseThrow, etc after peek method.
        assertThat(afterValOfOptinOfIsNotNullPeek.get()).isEqualTo("This is value after peek NotNull");

        Option<String> valOfOptinOfIsNull = Option.of(null); // it should be None.
        assertThat(valOfOptinOfIsNull.isDefined()).isFalse();
        assertThat(valOfOptinOfIsNull.isEmpty()).isTrue();
        assertThat(valOfOptinOfIsNull).isInstanceOf(Option.None.class);
        assertThatThrownBy(() -> valOfOptinOfIsNull.getOrElseThrow(MyNullPointerException::new))
                .isInstanceOf(MyNullPointerException.class); // Same like `Optional.orElseThrow`.

        // You can use peek method but nothing to show because peek just check value is present before invoke consume functoin value.
        String afterValOfOptinOfIsNullPeek = valOfOptinOfIsNull.peek(e -> System.out.println("This value is null."))
                .getOrElse("Something change"); // Nothing to print out when use peek on `None`.

        // You can use onEmpty to show value instead peek when value is None.
        valOfOptinOfIsNull.onEmpty(() -> System.out.println("This is no value."));

        assertThat(afterValOfOptinOfIsNullPeek).isEqualTo("Something change");
        // Or you can throw exception.
        assertThatThrownBy(() -> valOfOptinOfIsNull.peek(System.out::println)
                .getOrElseThrow(MyNullPointerException::new)).isInstanceOf(MyNullPointerException.class);


        Option<String> valOfOptionSomeFromNotNull = Option.some("NotNullable"); // it should be contain Some("NotNullable").
        assertThat(valOfOptionSomeFromNotNull.isDefined()).isTrue();
        assertThat(valOfOptionSomeFromNotNull.isEmpty()).isFalse();
        assertThat(valOfOptionSomeFromNotNull).isInstanceOf(Option.Some.class);
        // You can use peek to show value.
        valOfOptionSomeFromNotNull.peek(System.out::println);


        Option<String> valOfOptionSomeFromNull = Option.some(null); // it should be contain Some(null) value.
        assertThat(valOfOptionSomeFromNull.isDefined()).isTrue();
        assertThat(valOfOptionSomeFromNull.isEmpty()).isFalse();
        assertThat(valOfOptionSomeFromNull).isInstanceOf(Option.Some.class);
        // Peek can show `Some[null]` value.
        valOfOptionSomeFromNull.peek(e -> System.out.println("This is `" + e + "` value."));
        // Some[null] is not empty ( None ) value than this `onEmpty` nothing to show.
        valOfOptionSomeFromNull.onEmpty(() -> System.out.println("This is `None` value."));

        Option<String> valOfOptionNone = Option.none(); // it should be None type.
        assertThat(valOfOptionNone.isDefined()).isFalse();
        assertThat(valOfOptionNone.isEmpty()).isTrue();
        assertThat(valOfOptionNone).isInstanceOf(Option.None.class);
        // Nothing to show with `None` value.
        valOfOptionNone.peek(e -> System.out.println("This is ` " + e + "` value."));
        // Use `onEmpty` instead.
        valOfOptionNone.onEmpty(() -> System.out.println("This is `None` value."));


        // Difference between `orElse`
        // return other value.
        String a = (String) Optional.ofNullable(null).orElse("other value");
        assertThat(a).isEqualTo("other value");
        // return wrapped with container not like optional.
        Option<Object> b = Option.of(null).orElse(Option.some("other value"));
        assertThat(b.get()).isEqualTo("other value");
        test_option_to_stream();


    }

    @Test
    public void test_option_to_stream() {
        // transforming optional name
        List<Optional<String>> names = Arrays.asList(
                Optional.of("tom"),
                Optional.of("john"),
                Optional.of("emily")
        );

        List<String> newList = names.stream()
                .map(o -> toUpper(o))
                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
                .collect(toList());

        newList.forEach(System.out::println);

        // or
        List<String> newList2 = names.stream()
                .map(o -> toUpper(o))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        newList2.forEach(System.out::println);

        io.vavr.collection.List<Option<String>> namesOfVavr = io.vavr.collection.List.of(
                Option.of("tom"),
                Option.of("john"),
                Option.of("emily")
        );

        // with Vavr’s Option:
        io.vavr.collection.List<String> newList3 = namesOfVavr
                .map(o -> toUpper(o))
                .flatMap(e -> e.toStream());

        newList3.forEach(System.out::println);
    }

    private Option<String> toUpper(Option<String> o) {
        if ("tom".equals(o.get()) || "john".equals(o.get())) {
            return Option.of(String.format("Option value is %s", o.get().toUpperCase()));
        }
        return Option.none();
    }

    private Optional<String> toUpper(Optional<String> o) {
        if ("john".equals(o.get()) || "emily".equals(o.get())) {
            return Optional.of(String.format("Optional value %s", o.get().toUpperCase()));
        }
        return Optional.ofNullable(null);
    }

    static class MyNullPointerException extends RuntimeException {
    }

}
