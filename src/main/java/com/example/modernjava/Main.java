package com.example.modernjava;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        final StringBuilder builder = new StringBuilder();

        // 1. 반복문을 사용하여 numbers 사이에 : 추가하기
//        for (Integer number : numbers) {
//            builder.append(number).append(" : ");
//        }

        // 2. 마지막 " : " 제거하기
//      for (int i = 0; i < numbers.size(); i++) {
//      for 문에 numbers.size() 를 작성하면 반복시마다 numbers.size() 를 호출하여 비효율
        final int size = numbers.size();
        for (int i = 0; i < size; i++) {
            builder.append(numbers.get(i));
            if (i != size - 1) {
                builder.append(" : ");
            }
        }
        // 단점
        // 1. index 로 numbers 에 접근하기 때문에 index 를 잘 못 설정할 경우 ArrayIndexOutOfBoundsException 발생합니다.
        // 2. if 문이 어떤 의도인지 파악하기 쉽지 않습니다.

        System.out.println(builder);
    }
}
