package sample.vavr;


import io.vavr.control.Option;

public class SampleOption {

    public Option<Integer> makeOption(final Integer a){
        return Option.of(a);
    }
}
