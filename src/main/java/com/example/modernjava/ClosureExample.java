package com.example.modernjava;

public class ClosureExample {
    private int number = 999;

    public static void main(String[] args) {
        new ClosureExample().test1();
    }

    private void test1() {
        int number = 100;

        System.out.println("Anonymous Class");
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                System.out.println(number); // 100
//                System.out.println(this.number); // Error, Anonymous Class 인 Runnable 은 number 를 가지고 있지 않다.
                System.out.println(ClosureExample.this.number); // 999
            }
        });

        testClosure("Lambda Expression", () -> {
            System.out.println(number); // 100
            System.out.println(this.number); // 999
        });

        // number = 999 를 출력하고 싶은 경우는?
        // 1. number = 100 을 주석 처리
        // 2. ClosureExample.this.number = class name 을 지정
        // 이 때 lambda 에서는 this.number 가 999를 출력, class name 을 지정할 필요가 없음
        // Anonymous Class 와 Lambda 는 this 가 가리키는 대상에 차이가 있다.
        // Anonymous Class 에서의 this 는 Anonymous Class 를 가리키고,
        // Lambda 에서의 this 는 Lambda 를 가지고 있는 class 를 가리킨다.
        // Lambda 는 scope 를 가지고 있지 않다.

    }

    private static void testClosure(String name, Runnable runnable) {
        System.out.println("Start " + name + ": ");
        runnable.run();
        System.out.println("==========================");
    }

}
