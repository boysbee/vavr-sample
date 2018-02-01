package sample.vavr;

import io.vavr.concurrent.Promise;
import io.vavr.control.Either;

import static io.vavr.API.Try;

public class SamplePromise {
    public Promise<Integer> devide(final Integer dividend, final Integer divisor) {
        return Promise.fromTry(Try(() -> dividend / divisor));
    }
}
