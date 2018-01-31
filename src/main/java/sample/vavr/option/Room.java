package sample.vavr.option;

import io.vavr.control.Option;
import lombok.Data;

import java.util.List;

@Data
public class Room {
  private Option<List<Student>> students;
}
