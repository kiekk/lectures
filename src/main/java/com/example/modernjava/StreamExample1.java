package com.example.modernjava;

import java.util.stream.IntStream;

public class StreamExample1 {
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach(i -> System.out.print(i + " "));
        System.out.println();
        IntStream.iterate(1, i -> i + 1).forEach(i -> System.out.print(i + " "));
    }
}
