package com.example.modernjava;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExample5 {
    public static void main(String[] args) {
        int[] sum = {0};
        IntStream.range(0, 100)
                .forEach(i -> sum[0] += i);

        System.out.println("sum: " + sum[0]); // 4950

        int[] sum2 = {0};
        IntStream.range(0, 100)
                .parallel()
                .forEach(i -> sum2[0] += i);

        System.out.println("parallel sum (with side-effect): " + sum2[0]); // 4950 이 아님, 매번 다른 결과 (race condition)

        System.out.println("stream sum (no side-effect): " + IntStream.range(0, 100).sum());
        System.out.println("stream sum (no side-effect): " + IntStream.range(0, 100).parallel().sum()); // 결과가 같음

        System.out.println("==============================================");
        System.out.println("Parallel Stream");
        long start = System.currentTimeMillis();
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)
                .parallelStream()
                .peek(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(System.out::println);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println("====================================");
        System.out.println("Stream");
        start = System.currentTimeMillis();
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .peek(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .forEach(System.out::println);
        System.out.println(System.currentTimeMillis() - start);
    }
}
