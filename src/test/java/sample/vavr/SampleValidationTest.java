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
        assertThat(v.get()).isEqualTo("v");
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



}
