package sample.option;

import lombok.val;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {


  @Test
  public void testOptional() {
    val student = new Student();
    val address = new Address();
    Location location = new Location();
    location.setLocation("Minburi");
    address.setLocation(Optional.of(location));
    student.setAddress(Optional.of(address));
    String locate = retrieveAddress(student)
            .flatMap(a -> a.getLocation())
            .flatMap(l -> Optional.of(l.getLocation()))
            .filter(b -> b.equalsIgnoreCase("Minburi"))
            .orElse("Not Found");
    System.out.println(locate);
    assertThat(locate).isEqualTo("Minburi");
  }

  @Test
  public void testOptionalNull() {
    val student = new Student();

    String locate = Optional.ofNullable(student.address)
            .filter(Optional::isPresent)
            .flatMap(address -> address)
            .flatMap(Address::getLocation)
            .flatMap(l -> Optional.of(l.getLocation()))
            .filter(b -> b.equalsIgnoreCase("Some Where"))
            .orElse("Not Found");
    System.out.println(locate);
    assertThat(locate).isEqualTo("Not Found");
  }

  private Optional<Address> retrieveAddress(Student student) {
    return student.getAddress();
  }
}