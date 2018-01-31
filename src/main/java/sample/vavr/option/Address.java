package sample.vavr.option;

import io.vavr.control.Option;
import lombok.Data;

@Data
public class Address {
  Option<Location> location;
}
