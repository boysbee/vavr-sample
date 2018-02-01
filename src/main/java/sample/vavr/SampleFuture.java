package sample.vavr;


import io.vavr.concurrent.Future;

public class SampleFuture {

    public Future<Integer> divide(final Integer dividend, final Integer divisor) {
        return Future.of(() -> dividend / divisor);
    }
}
