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

        // Stream Integer 의 경우 -127 ~ 128 까지는 caching 함

        // 127 까지는 caching 이 되어 있기 때문에 찾을 수 있음
        Integer integer127 = 127;
        System.out.println(Stream.of(1, 2, 3, 4, 5, 127)
                .filter(i -> i == integer127)
                .findFirst()); // Optional[127]

        // 128 은 caching 이 되어 있지 않기 때문에 찾을 수 없음
        Integer integer128 = 128;
        System.out.println(Stream.of(1, 2, 3, 4, 5, 128)
                .filter(i -> i == integer128)
                .findFirst()); // Optional.empty

        // equals 를 호출하면 비교 가능
        System.out.println(Stream.of(1, 2, 3, 4, 5, 128)
                .filter(i -> i.equals(integer128))
                .findFirst()); // Optional[128]
    }
}
