package sample.vavr;


import io.vavr.Tuple2;
import io.vavr.collection.List;

public class SampleList {

    public List<Integer> makeList(final Integer... inputs) {
        return List.of(inputs);
    }
}
