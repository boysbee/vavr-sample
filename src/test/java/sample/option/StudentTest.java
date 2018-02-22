package sample.option;

import io.vavr.control.Option;
import lombok.Data;
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

    @Test(expected = CustomExceptoin.class)
    public void testOptionalThrowFromNull() throws Exception {
        val student = new SomeStudent();
        student.setAddress(Option.of(null));

        assertThat(student.getAddress().getOrElseThrow(() -> new CustomExceptoin()).getLocation()).isEqualTo("");

    }

    private Optional<Address> retrieveAddress(Student student) {
        return student.getAddress();
    }

    @Data
    static class SomeStudent {
        private Option<Address> address;
    }

    @Data
    static class SomeAddress {
        private String location;
    }
}

class CustomExceptoin extends Exception {
}