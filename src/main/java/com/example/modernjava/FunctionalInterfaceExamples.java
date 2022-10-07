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

        

    }

}
