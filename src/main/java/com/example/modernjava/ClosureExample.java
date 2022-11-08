package com.example.modernjava;

public class ClosureExample {
    public static void main(String[] args) {
        int number = 100;

        Runnable runnable = () -> System.out.println(number);

        runnable.run(); // 100

        // 람다식 에서 외부 변수를 참조하는 것 = 클로저 (Closure)
        // scope 확장, 람다 scope 를 외부 scope 까지로 확장
    }
}
