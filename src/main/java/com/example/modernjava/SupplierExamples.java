package com.example.modernjava;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class SupplierExamples {

    public static void main(String[] args) {
        Supplier<String> helloSupplier = () -> "Hello ";

        System.out.println(helloSupplier.get() + "world");

        // 왜 굳이 Supplier 를 사용해야 하는가?

        long start = System.currentTimeMillis();
        printIfValid(0, () -> "Hello");
        printIfValid(1, () -> "Hello");
        printIfValid(-1, () -> "Hello");
        System.out.println("It took : " + ((System.currentTimeMillis() - start) / 1000) + " seconds");

        long start2 = System.currentTimeMillis();
        printIfValid(0, SupplierExamples::getVeryExpensiveValue);
        printIfValid(1, SupplierExamples::getVeryExpensiveValue);
        printIfValid(-1, SupplierExamples::getVeryExpensiveValue);

        // Supplier 적용시 실제 값을 사용할 때 호출
        // 실제 값이 출력되는 printIfValid(0, SupplierExamples::getVeryExpensiveValue); printIfValid(1, SupplierExamples::getVeryExpensiveValue); 만 실행되어
        // 총 6초의 시간이 소요
        System.out.println("It took : " + ((System.currentTimeMillis() - start2) / 1000) + " seconds");
    }

    // 생성 비용이 매우 비싼 객체를 반환받는다고 가정
    private static String getVeryExpensiveValue() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello";
    }

    private static void printIfValid(int number, Supplier<String> valueSupplier) {
        // 조건에 부합할 경우에만 value 가 사용됨.
        if (number >= 0) {
            System.out.println("The value is " + valueSupplier.get() + ".");
        } else {
            System.out.println("Invalid");
        }
    }

}
