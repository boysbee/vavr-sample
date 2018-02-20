package sample.vavr;


import io.vavr.control.Validation;

public class SampleValidation {

    private SampleValidation() {
    }

    public static Validation<String, String> validateName(final String n) {
        return (n.length() > 1) ? ("test".equalsIgnoreCase(n)) ? Validation.valid("Ok") : Validation.invalid("Error") : Validation.invalid("Error");
    }

    public static Validation<String, String> validateAddress(final String n) {
        return (n.length() > 1) ? ("test".equalsIgnoreCase(n)) ? Validation.valid("Ok") : Validation.invalid("Wrong Address") : Validation.invalid("Wrong Address");
    }
}
