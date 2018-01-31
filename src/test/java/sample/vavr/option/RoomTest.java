package sample.vavr.option;


import lombok.val;
import org.junit.Test;

import static io.vavr.API.Some;
import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {


  @Test
  public void test() {
    val student = new Student();

    String locate = Some(student.getAddress())
            .flatMap(a -> a)
            .flatMap(Address::getLocation)
            .flatMap(Location::getLocation)
            .getOrElse("Not Found");

    System.out.println(locate);
    assertThat(locate).isEqualTo("Not Found");

  }
}