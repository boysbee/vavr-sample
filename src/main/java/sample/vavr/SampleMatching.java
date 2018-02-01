package sample.vavr;


import io.vavr.control.Try;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Failure;
import static io.vavr.Patterns.$Success;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.instanceOf;

public class SampleMatching {

    public String devide(final Integer dividend, final Integer divisor) {
        return Match(Try(() -> (dividend / divisor))).of(
                Case($Success($()), "Ok"),
                Case($Failure($()), "Fail")
        );
    }

    public String test(final Object o) {
        return Match(o).of(
                Case($(is("ok")), "Yes is Ok"),
                Case($(instanceOf(Integer.class)), i -> String.valueOf(i)),
        Case($(), "Yes is Other")
        );
    }
}
