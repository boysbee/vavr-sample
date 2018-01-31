package sample.option;

import lombok.Data;

import java.util.Optional;

@Data
public class Student {
  String name;
  Integer age;

  Optional<Address> address;
}
