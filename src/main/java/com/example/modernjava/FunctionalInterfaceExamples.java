package com.example.modernjava;

public class FunctionalInterfaceExamples {

//    public static void main(String[] args) {
//        // Function 사용
//        // 1. 익명 클래스
//        Function<String, Integer> toInt = new Function<String, Integer>() {
//            @Override
//            public Integer apply(String s) {
//                return Integer.parseInt(s);
//            }
//        };
//
//        Integer result = toInt.apply("100");
//        System.out.println(result);
//
//        // 2. 람다식
//        Function<String, Integer> toInt2 = s -> Integer.parseInt(s);
//
//        Integer result2 = toInt2.apply("100");
//        System.out.println(result2);
//
//        // 3. 메소드 참조
//        Function<String, Integer> toInt3 = Integer::parseInt;
//
//        Integer result3 = toInt3.apply("100");
//        System.out.println(result3);
//
//        Function<Integer, Integer> identity = Function.identity();
//        System.out.println(identity.apply(999));
//
//        Function<Integer, Integer> identity2 = integer -> integer;
//        System.out.println(identity2.apply(999));
//
//    }

    public static void main(String[] args) {
        println(1, 2, 3, (integer, integer2, integer3) -> String.valueOf(integer + integer2 + integer3));
        println("Area is ", 12, 20, (message, length, width) -> message + (length * width));
        println(1L, "kevin", "test@email.com", (id, name, email) -> "User info: ID=" + id + ", NAME=" + name + ", EMAIL=" + email);
    }

    private static <T1, T2, T3> void println(T1 t1, T2 t2, T3 t3, CustomFunction<T1, T2, T3, String> function) {
        System.out.println(function.apply(t1, t2, t3));
    }
}

@FunctionalInterface // @FunctionalInterface 어노테이션을 사용해서 컴파일 타임에 에러를 확인
interface CustomFunction<T1, T2, T3, R> {
    R apply(T1 t1, T2 t2, T3 t3);

    // 추상 메소드가 2개 이상 있을 경우 에러 발생
//    void print(int i);
}
