package com.example.modernjava;

import java.util.function.Predicate;

public class PredicateExamples {

    public static void main(String[] args) {

        // Predicate 예제
        // 1. 람다식
        Predicate<Integer> isPositive = integer -> integer > 0;

        System.out.println(isPositive.test(1));
        System.out.println(isPositive.test(0));
        System.out.println(isPositive.test(-1));
    }

}
