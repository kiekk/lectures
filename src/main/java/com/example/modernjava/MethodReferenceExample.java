package com.example.modernjava;

import java.util.Arrays;

public class MethodReferenceExample {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);
    }
}
