package com.example.modernjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class HigherOrderFunctionExamples {
    public static void main(String[] args) {
        Function<Function<Integer, String>, String> f = g -> g.apply(10);

        System.out.println(f.apply(i -> "#" + i));

        Function<Integer, Function<Integer, Integer>> f2 = i -> i2 -> i + i2;
        System.out.println(f2.apply(1).apply(9));

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(map(list, i -> "#" + i));
    }

    // First Class Citizen
    // 1. 변수나 데이터 구조에 담을 수 있다.
    // 2. 파라미터로 전달할 수 있다.
    // 3. 반환값으로 사용할 수 있다.
    // 4. 고유한 식별이 가능하다.

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}
