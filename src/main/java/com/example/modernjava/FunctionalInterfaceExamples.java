package com.example.modernjava;

import java.util.function.Function;

public class FunctionalInterfaceExamples {

    public static void main(String[] args) {
        // Function 사용
        // 1. 익명 클래스
        Function<String, Integer> toInt = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s);
            }
        };

        Integer result = toInt.apply("100");
        System.out.println(result);

        // 2. 람다식
        Function<String, Integer> toInt2 = s -> Integer.parseInt(s);

        Integer result2 = toInt2.apply("100");
        System.out.println(result2);

        // 3. 메소드 참조
        Function<String, Integer> toInt3 = Integer::parseInt;

        Integer result3 = toInt3.apply("100");
        System.out.println(result3);

    }

}
