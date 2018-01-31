package sample.vavr.option;

import io.vavr.control.Option;
import lombok.Data;

@Data
public class Student {
  String name;
  Integer age;

  Option<Address> address;
}
