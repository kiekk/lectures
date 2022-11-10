package com.example.modernjava;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodReferenceExample {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);

        // sorting
        System.out.println(Stream.of(new BigDecimal("10.0"), new BigDecimal("23"), new BigDecimal("5"))
                .sorted(BigDecimal::compareTo)
//                .sorted(BigDecimalUtil::compare)
                .collect(Collectors.toList()));


        // instance method reference
        System.out.println(Stream.of("a", "b", "c", "d")
                .anyMatch("c"::equals));
//                .anyMatch(s -> s.equals("c")));
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}

