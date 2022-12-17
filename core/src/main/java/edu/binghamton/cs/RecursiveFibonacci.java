package edu.binghamton.cs;

/** A fibonacci that recursively computes the nth fibonacci number. */
public final class RecursiveFibonacci implements Fibonacci {
  @Override
  public int compute(int n) {
    if (n == 0 || n == 1) {
      return n;
    } else {
      return compute(n - 1) + compute(n - 2);
    }
  }
}
