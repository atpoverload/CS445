package edu.binghamton.cs;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.Blackhole;

@Fork(value = 2)
@Warmup(iterations = 3, time = 15)
@Measurement(iterations = 5, time = 60)
@State(Scope.Benchmark)
public class FibonacciBenchmark {
  @State(Scope.Benchmark)
  public static class FibonacciState {
    public int n;
    public Fibonacci fibonacci;

    public void init(int n, Fibonacci fibonacci) {
      this.n = n;
      this.fibonacci = fibonacci;
    }

    @Setup(Level.Iteration)
    public void setup(BenchmarkParams params) throws Exception {
      init(
          Integer.parseInt(N),
          (Fibonacci)
              Class.forName(String.join(".", "edu.binghamton.cs", fibonacciType))
                  .getConstructor()
                  .newInstance());
    }
  }

  @Param({"10", "15", "20", "25", "30"})
  public static String N;

  @Param({
    "TabulatedFibonacci",
    "RecursiveFibonacci",
    "MemoizedRecursiveFibonacci",
    "ConcurrentFibonacci"
  })
  public static String fibonacciType;

  @Benchmark
  public void test(FibonacciState state) {
    Blackhole.consumeCPU(state.fibonacci.compute(state.n));
  }
}
