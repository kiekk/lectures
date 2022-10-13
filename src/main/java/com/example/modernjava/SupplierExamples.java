package com.example.modernjava;

import java.util.function.Supplier;

public class SupplierExamples {

    public static void main(String[] args) {
        Supplier<String> helloSupplier = () -> "Hello ";

        System.out.println(helloSupplier.get() + "world");

        // 왜 굳이 Supplier 를 사용해야 하는가?

        printIfValid(0, "Hello");
        printIfValid(1, "Hello");
        printIfValid(-1, "Hello");
    }

    private static void printIfValid(int number, String value) {
        // 조건에 부합할 경우에만 value 가 사용됨.
        if (number >= 0) {
            System.out.println("The value is " + value + ".");
        } else {
            System.out.println("Invalid");
        }
    }

}
