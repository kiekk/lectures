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
                System.out.println(this); // com.example.modernjava.ClosureExample$1@ed17bee
                System.out.println(ClosureExample.this); // ClosureExamples{number=999}

//                System.out.println(toString("test")); // Error
                // Anonymous Class 에서는 가지고 있는 메소드(상속한 메소드 포함)와 이름이 동일한 외부 메소드에 접근할 경우 shadowing 이 발생한다.
                // shadowing 이 발생할 경우 가장 가까이 있는 클로저를 사용, 이 때는 Runnable 을 사용하는데 Runnable 에 toString 을 호출하게 됨

                System.out.println("toString(): " + toString()); // toString(): com.example.modernjava.ClosureExample$1@ed17bee
                System.out.println("ClosureExample.toString(): " + ClosureExample.toString("test")); // ClosureExample.toString(): The value is test
                // 외부 scope 의 메소드를 사용하려면 변수 때와 마찬가지로 class name 을 지정
            }
        });

        testClosure("Lambda Expression", () -> {
//            int number = 50; // Error
            // Variable 'number' is already defined in the scope
            // lambda 에서는 scope 가 확장되기 때문에 외부 scope 의 number 를 찾고, number 가 있다고 판단하여 에러 발생

            System.out.println(number); // 100
            System.out.println(this.number); // 999
            System.out.println(this); // ClosureExamples{number=999}

            System.out.println(toString("test"));
            // Lambda 에서는 shadowing 이 발생하지 않음
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

    @Override
    public String toString() {
        return "ClosureExamples{" +
                "number=" +
                number +
                "}";
    }

    public static <T> String toString(T value) {
        return "The value is " + String.valueOf(value);
    }
}
