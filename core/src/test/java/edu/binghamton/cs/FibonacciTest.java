package edu.binghamton.cs;

import static edu.binghamton.cs.TabulatedFibonacci.FIBONACCI_NUMBERS;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FibonacciTest {
  private static final int N = FIBONACCI_NUMBERS.length - 1;

  @Parameter public Fibonacci fibonacci;

  @Parameters(name = "{0}")
  public static Iterable<Object[]> params() {
    return Arrays.asList(
        new Object[][] {
          {new TabulatedFibonacci()},
          {new RecursiveFibonacci()},
          {new MemoizedRecursiveFibonacci()},
          // TODO: this slows down the test considerably
          {new ConcurrentFibonacci()}
        });
  }

  @Test
  public final void assertCorrect() {
    for (int n = 0; n <= N; n++) {
      assertEquals(FIBONACCI_NUMBERS[n], fibonacci.compute(n));
    }
  }
}
