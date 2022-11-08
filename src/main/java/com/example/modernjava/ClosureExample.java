package com.example.modernjava;

public class ClosureExample {
    public static void main(String[] args) {
        int number = 100; // Effectively Final = final 처럼 보이지는 않지만 final 로 동작

        System.out.println("Anonymous Class");
        testClosure("Anonymous Class", new Runnable() {
            @Override
            public void run() {
                // number = 200;
                // Variable 'number' is accessed from within inner class, needs to be final or effectively final
                System.out.println(number);
            }
        });

        // number = 200;
        // Variable used in lambda expression should be final or effectively final
        testClosure("Lambda Expression", () -> System.out.println(number));

        /*
            Anonymous Class vs Lambda Expression

           java 7 까지는 외부 변수가 final 이 아닐 경우 error 발생
           java 8 부터는 final 이 아니어도 접근 가능

           대신 외부 scope 의 변수를 read (읽기) 는 가능,
           외부 scope 의 변수를 update (조작) 하는 것은 불가능
         */
    }

    private static void testClosure(String name, Runnable runnable) {
        System.out.println("Start " + name + ": ");
        runnable.run();
        System.out.println("==========================");
    }
}
