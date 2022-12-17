package edu.binghamton.cs;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/** Simple server that provides recursive computation of fibonacci numbers. */
public final class FibonacciServer {
  private static class FibonacciService extends FibonacciGrpc.FibonacciImplBase {
    private final Fibonacci fibonacci;

    private FibonacciService(Fibonacci fibonacci) {
      this.fibonacci = fibonacci;
    }

    @Override
    public void fibonacci(
        FibonacciRequest request, StreamObserver<FibonacciResponse> responseObserver) {
      responseObserver.onNext(
          FibonacciResponse.newBuilder().setF(fibonacci.compute(request.getN())).build());
      responseObserver.onCompleted();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 50051;
    final Server server =
        ServerBuilder.forPort(port)
            .addService(new FibonacciService(new RecursiveFibonacci()))
            .build()
            .start();
    server.awaitTermination();
  }
}
