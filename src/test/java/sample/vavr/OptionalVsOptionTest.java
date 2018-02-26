package sample.vavr;

import io.vavr.control.Option;
import org.junit.Test;

import java.util.Optional;

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
        String afterValOfOptinOfIsNullPeek = valOfOptinOfIsNull.peek(System.out::println)
                .getOrElse("Something change"); // Nothing to print out when use peek on `None`.
        assertThat(afterValOfOptinOfIsNullPeek).isEqualTo("Something change");
        // Or you can throw exception.
        assertThatThrownBy(() -> valOfOptinOfIsNull.peek(System.out::println)
                .getOrElseThrow(MyNullPointerException::new)).isInstanceOf(MyNullPointerException.class);


        Option<String> valOfOptionSomeFromNotNull = Option.some("NotNullable"); // it should be contain Some("NotNullable").
        assertThat(valOfOptionSomeFromNotNull.isDefined()).isTrue();
        assertThat(valOfOptionSomeFromNotNull.isEmpty()).isFalse();
        assertThat(valOfOptionSomeFromNotNull).isInstanceOf(Option.Some.class);

        Option<String> valOfOptionSomeFromNull = Option.some(null); // it should be contain Some(null) value.
        assertThat(valOfOptionSomeFromNull.isDefined()).isTrue();
        assertThat(valOfOptionSomeFromNull.isEmpty()).isFalse();
        assertThat(valOfOptionSomeFromNull).isInstanceOf(Option.Some.class);

        Option<String> valOfOptionNone = Option.none(); // it should be None type.
        assertThat(valOfOptionNone.isDefined()).isFalse();
        assertThat(valOfOptionNone.isEmpty()).isTrue();
        assertThat(valOfOptionNone).isInstanceOf(Option.None.class);

    }

    static class MyNullPointerException extends RuntimeException {
    }

}
