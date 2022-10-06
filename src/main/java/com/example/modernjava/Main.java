package com.example.modernjava;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        final StringBuilder builder = new StringBuilder();

        // 1. 반복문을 사용하여 numbers 사이에 : 추가하기
        for (Integer number : numbers) {
            builder.append(number).append(" : ");
        }

        System.out.println(builder);
    }
}
