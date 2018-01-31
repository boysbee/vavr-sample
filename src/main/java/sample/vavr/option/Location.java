package sample.vavr.option;

import io.vavr.control.Option;
import lombok.Data;

@Data
public class Location {
  Option<String> location;
}
