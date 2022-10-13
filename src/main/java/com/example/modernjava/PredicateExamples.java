package com.example.modernjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateExamples {

    public static void main(String[] args) {

        // Predicate 예제
        // 1. 람다식
        Predicate<Integer> isPositive = integer -> integer > 0;

        System.out.println(isPositive.test(1));
        System.out.println(isPositive.test(0));
        System.out.println(isPositive.test(-1));

        List<Integer> numbers = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);
        List<Integer> positiveNumbers = new ArrayList<>();

        // 2. 반복문에서 Predicate 사용
        for (Integer number : numbers) {
            if (isPositive.test(number)) {
                positiveNumbers.add(number);
            }
        }

        System.out.println(positiveNumbers);

        // 3. stream, filter 사용
        List<Integer> numbers2 = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);
        List<Integer> positiveNumbers2 = numbers2.stream().filter(isPositive).collect(Collectors.toList());

        System.out.println(positiveNumbers2);

        // 4. method 사용
        // Predicate 조건을 동적으로 전달
        List<Integer> numbers3 = Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5);

        System.out.println(filter(numbers3, integer -> integer > 0));
        System.out.println(filter(numbers3, integer -> integer < 3));

    }

    private static <T> List<T> filter(List<T> list, Predicate<T> filter) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (filter.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

}
