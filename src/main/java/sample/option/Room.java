package sample.option;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class Room {
  private Optional<List<Student>> students;
}
