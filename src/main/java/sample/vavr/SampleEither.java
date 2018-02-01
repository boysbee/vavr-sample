package sample.vavr;


import io.vavr.control.Either;

import static io.vavr.API.Try;

public class SampleEither {

    public Either<Throwable, Integer> devide(final Integer dividend, final Integer divisor) {
        return Try(() -> dividend / divisor).toEither();
    }
}
