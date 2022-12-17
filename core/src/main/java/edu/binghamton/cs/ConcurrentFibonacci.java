package edu.binghamton.cs;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/** Computes the fibonacci number by submitting the child computations to a thread pool. */
// TODO: very slow; make a memoizing implementation
public final class ConcurrentFibonacci implements Fibonacci {
  private final ScheduledThreadPoolExecutor executor =
      new ScheduledThreadPoolExecutor(
          Runtime.getRuntime().availableProcessors(),
          r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
          });
  // TODO: operations on this are the critical path
  private final ConcurrentLinkedQueue<Future<Integer>> tasks = new ConcurrentLinkedQueue<>();

  @Override
  public int compute(int n) {
    int f = computeHelper(n);
    // TODO: find a better synchronization mechanism
    while (!tasks.isEmpty() || executor.getTaskCount() - executor.getCompletedTaskCount() > 0) {
      try {
        if (tasks.isEmpty()) {
          Thread.sleep(1);
        } else {
          f += tasks.poll().get();
        }
      } catch (Exception e) {
        System.out.println(tasks);
        e.printStackTrace();
      }
    }
    return f;
  }

  private int computeHelper(int n) {
    if (n == 0 || n == 1) {
      return n;
    } else {
      tasks.add(executor.submit(() -> computeHelper(n - 1)));
      tasks.add(executor.submit(() -> computeHelper(n - 2)));
      return 0;
    }
  }
}
