package sample.vavr;


import io.vavr.control.Validation;

import java.util.regex.Pattern;

public class SampleValidation {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private SampleValidation() {
    }

    public static Validation<String, String> validateName(final String n) {
        return (n.length() > 1) ? ("test".equalsIgnoreCase(n)) ? Validation.valid("Ok") : Validation.invalid("Error") : Validation.invalid("Error");
    }

    public static Validation<String, String> validateAddress(final String n) {
        return (n.length() > 1) ? ("test".equalsIgnoreCase(n)) ? Validation.valid("Ok") : Validation.invalid("Wrong Address") : Validation.invalid("Wrong Address");
    }

    public static Validation<String, String> validateEmail(final String n) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        return (n.length() > 1) ? pattern.matcher(n).matches() ? Validation.valid("Ok") : Validation.invalid("Wrong email") : Validation.invalid("Wrong email");
    }
}
