package edu.binghamton.cs;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Arrays;

/** Client to parse a single arg to int and request its fibonacci number. */
public final class FibonacciClient {
  private static String getOrdinalName(int n) {
    return n / 10 == 1 ? "th" : n % 10 == 1 ? "st" : n % 10 == 2 ? "nd" : n % 10 == 3 ? "rd" : "th";
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println(
          String.format("excepted a single non-zero integer, got %s", Arrays.toString(args)));
    }
    int n = Integer.parseInt(args[0]);

    ManagedChannel channel =
        ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

    FibonacciGrpc.FibonacciBlockingStub stub = FibonacciGrpc.newBlockingStub(channel);

    FibonacciResponse response =
        stub.fibonacci(FibonacciRequest.newBuilder().setN(Integer.parseInt(args[0])).build());

    System.out.println(
        String.format("The %d%s fibonacci number is %d.", n, getOrdinalName(n), response.getF()));

    channel.shutdown();
  }
}
