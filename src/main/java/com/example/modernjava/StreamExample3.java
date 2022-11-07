package com.example.modernjava;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample3 {
    public static void main(String[] args) {
        // 특정 값을 return 하는 것은 Terminal Operation Method : 최종 연산
        // Stream 을 return 하는 것은 Intermediate Operation Method : 중간 연산

        // Stream 은 Lazy Execution
        // Terminal Operation Method (최종 연산) 호출 전까지는 중간 연산을 실행 X

        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5)
                .filter(i -> {
                    System.out.println("filter");
                    return i > 2;
                })
                .map(i -> {
                    System.out.println("map");
                    return i * 2;
                });

        System.out.println(integerStream); // 이 때 까지는 중간 연산 실행 X

        integerStream.collect(Collectors.toList()); // 최종 연산 호출 시 실행
    }
}
