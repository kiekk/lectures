package com.example.modernjava;

import java.util.function.Consumer;

public class ConsumerExamples {

    public static void main(String[] args) {

        // Consumer 예제
        // 1. 익명 클래스
        Consumer<String> print = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        print.accept("hello");

        // 2. 람다식
        Consumer<String> print2 = s -> System.out.println(s);

        print2.accept("hello");
    }

}
