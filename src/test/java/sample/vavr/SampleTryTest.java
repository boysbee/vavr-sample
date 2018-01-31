package sample.vavr;

import lombok.val;
import org.junit.Test;

public class SampleTryTest {

  @Test
  public void tryPlus2Integer() {
    val sample = new SampleTry();
    sample.plus(1, 2)
            .onSuccess(System.out::println);
  }

  @Test
  public void tryDivideOnSuccessWhenDivisorNotZero() {
    val sample = new SampleTry();
    sample.divide(1, 1)
            .onSuccess(System.out::println);
  }

  @Test
  public void tryDivideOnFailureWhenDivisorIsZero() {
    val sample = new SampleTry();
    sample.divide(1, 0)
            .onFailure(e -> System.out.println(e.getMessage()));
  }
}