package com.example.modernjava;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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

        methodReference3();
    }

    private static void methodReference3() {
        // using lambda expression
        System.out.println(testFirstClassFunction1(3, integer -> String.valueOf(integer * 2)));

        // using method reference
        System.out.println(testFirstClassFunction1(3, MethodReferenceExample::doubleThenToString));

        // using lambda expression in function
        Function<Integer, String> fl = getDoubleThenToStringUsingLambdaExpression();
        String resultFromFl = fl.apply(3);
        System.out.println(resultFromFl);

        Function<Integer, String> fmr = getDoubleThenToStringUsingMethodReference();
        String resultFromFmr = fmr.apply(3);
        System.out.println(resultFromFmr);

        System.out.println("------------------------------------------------");
        // lambda expression
        List<Function<Integer, String>> fsL = Arrays.asList(integer -> String.valueOf(integer * 2));
        for (Function<Integer, String> f : fsL) {
            String result = f.apply(3);
            System.out.println(result);
        }

        System.out.println("------------------------------------------------");
        // method reference
        List<Function<Integer, String>> fsMr = Arrays.asList(MethodReferenceExample::doubleThenToString);
        for (Function<Integer, String> f : fsMr) {
            String result = f.apply(3);
            System.out.println(result);
        }
    }

    private static String testFirstClassFunction1(int n, Function<Integer, String> f) {
        return "The result is " + f.apply(n);
    }

    private static String doubleThenToString(int n) {
        return String.valueOf(n * 2);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingLambdaExpression() {
        return i -> String.valueOf(i * 2);
    }

    private static Function<Integer, String> getDoubleThenToStringUsingMethodReference() {
        return MethodReferenceExample::doubleThenToString;
    }
}

class BigDecimalUtil {
    public static int compare(BigDecimal bd1, BigDecimal bd2) {
        return bd1.compareTo(bd2);
    }
}

