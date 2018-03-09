package sample.vavr;

import io.vavr.control.Validation;
import lombok.val;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleValidationTest {

    @Test
    public void testValidationInValid() {
        val v = SampleValidation.validateName("v");
        assertThat(v.isInvalid()).isTrue();
        // get error value when invalid
        assertThat(v.getError()).isEqualTo("Error");
    }

    @Test
    public void testValidationInValidMapError() {
        val v = SampleValidation.validateName("v").mapError(String::toUpperCase);

        assertThat(v.getError()).isEqualTo("ERROR");
    }

    @Test
    public void testValidationValid() {
        val v = SampleValidation.validateName("Test");
        assertThat(v.isValid()).isTrue();
        assertThat(v.get()).isEqualTo("Ok");
    }

    @Test
    public void testCombine() {
        String name = "Test";
        String address = "Test";
        Validation v = Validation.combine(
                SampleValidation.validateName(name),
                SampleValidation.validateAddress(address)
        ).ap((n, a) -> String.format("Name is %s and Address is %s", n, a));

        assertThat(v.isValid()).isTrue();
        assertThat(v.get()).isEqualTo("Name is Ok and Address is Ok");
    }

    @Test
    public void testCombine2WithOneResponseInvalid() {
        String name = "Test";
        String address = "Wrong";
        Validation v = Validation.combine(
                SampleValidation.validateName(name),
                SampleValidation.validateAddress(address)
        ).ap((n, a) -> String.format("Name is %s and Address is %s", n, a));

        assertThat(v.isInvalid()).isTrue();
    }

    @Test
    public void testCombine3WithValid() {

        Validation v = Validation.combine(
                Validation.valid(true),
                Validation.valid(true),
                Validation.valid(true)
        ).ap((a, b, c) -> a && b && c);

        assertThat(v.isValid()).isTrue();
    }

    @Test
    public void testCombineWithValidationIsValid() {

        Validation v = Validation.combine(
                SampleValidation.validateName("test"),
                SampleValidation.validateAddress("test"),
                SampleValidation.validateEmail("myemail@email.com")
        ).ap((a, b, c) -> a + b + c);

        assertThat(v.isValid()).isTrue();


    }

    @Test
    public void testCombineValidationIsInvalid() {
        Validation v = Validation.combine(
                SampleValidation.validateName("something"),
                SampleValidation.validateAddress("_"),
                SampleValidation.validateEmail("email.com")
        ).ap((a, b, c) -> a + b + c)
                .mapError(e -> e.foldLeft("", (a, b) -> {
                    return a + " " + b;
                }));

        assertThat(v.isInvalid()).isTrue();
        assertThat(v.getError()).isEqualTo(" Error Wrong Address Wrong email");

        String message = (String) v.fold(invalid -> {
            return invalid;
        }, valid -> {
            return valid;
        });
        assertThat(message).isEqualTo(" Error Wrong Address Wrong email");
    }

    @Test
    public void testCombineValidationIsInValidWithFlatMap() {

        assertThat(SampleValidation.validateName("something")
                .flatMap(a -> SampleValidation.validateAddress("_")
                        .flatMap(b -> SampleValidation.validateEmail("email.com")))
                .isInvalid()).isTrue();
    }

    @Test
    public void testCombineValidationIsValidWithFlatMap() {

        assertThat(SampleValidation.validateName("test")
                .flatMap(a -> SampleValidation.validateAddress("test")
                        .flatMap(b -> SampleValidation.validateEmail("mymail@email.com")))
                .isValid()).isTrue();
    }

}
