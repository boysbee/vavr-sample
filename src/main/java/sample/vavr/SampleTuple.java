package sample.vavr;


import io.vavr.Tuple2;
import io.vavr.control.Either;

import static io.vavr.API.Try;

public class SampleTuple {

    public Tuple2<String, Integer> makeTuple(){
        return new Tuple2<>("Good",1);
    }
}
