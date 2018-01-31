package sample.option;

import lombok.Data;

import java.util.Optional;

@Data
public class Address {
  Optional<Location> location;
}
