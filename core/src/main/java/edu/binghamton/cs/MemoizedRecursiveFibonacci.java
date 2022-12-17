package edu.binghamton.cs;

import java.util.Arrays;

/** A fibonacci that stores each n > 1 as it computes it. Not thread safe. */
public final class MemoizedRecursiveFibonacci implements Fibonacci {
  private static final int getNearestPowerOfTwo(int n) {
    return (int) (Math.log(n - 2) / Math.log(2));
  }

  private static final int INITIAL_CACHE_SIZE = 4;

  private int[] cache = new int[INITIAL_CACHE_SIZE - 2];

  @Override
  public int compute(int n) {
    if (n == 0 || n == 1) {
      return n;
    }

    if (n - 2 >= cache.length) {
      cache = Arrays.copyOf(cache, Math.max(2 << getNearestPowerOfTwo(n), 2 * cache.length));
    }

    if (cache[n - 2] != 0) {
      return cache[n - 2];
    } else {
      int f = compute(n - 1) + compute(n - 2);

      cache[n - 2] = f;
      return f;
    }
  }
}
