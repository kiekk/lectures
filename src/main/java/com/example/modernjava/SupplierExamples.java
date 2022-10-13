package com.example.modernjava;

import java.util.function.Supplier;

public class SupplierExamples {

    public static void main(String[] args) {
        Supplier<String> helloSupplier = () -> "Hello ";

        System.out.println(helloSupplier.get() + "world");

        // 왜 굳이 Supplier 를 사용해야 하는가?
    }

}
