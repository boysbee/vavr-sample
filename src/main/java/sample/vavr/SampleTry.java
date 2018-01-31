package sample.vavr;

import io.vavr.control.Option;
import io.vavr.control.Try;

import static io.vavr.API.Try;

public class SampleTry {
  public Try<Integer> plus(final int i, final int x) {
    return Try(() -> (i + x));
  }

  public Try<Integer> divide(final Integer dividend, final Integer divisor) {
    return Try.of(() -> dividend / divisor);
  }
}
