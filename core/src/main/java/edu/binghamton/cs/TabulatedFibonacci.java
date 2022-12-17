package edu.binghamton.cs;

/** A fibonacci that looks up values from an array, up to the 25th number. */
public final class TabulatedFibonacci implements Fibonacci {
  static final int[] FIBONACCI_NUMBERS =
      new int[] {
        0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765,
        10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040
      };

  @Override
  public int compute(int n) {
    return FIBONACCI_NUMBERS[n];
  }
}
