package com.example.modernjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StreamExample1 {
    public static void main(String[] args) {
        // Stream 은 일종의 Collection Builder

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = new ArrayList<>();

        // 일반적인 순회 / 필터
        for (Integer number : numbers) {
            if (number > 3 && number < 9) {
                Integer newNumber = number * 2; // 기존의 숫자에 연산을 추가
                // 이 로직은 조건에 맞는 데이터를 찾는 필터링인데,
                // 이 로직에 추가 연산이 들어가야 하는가?
                result.add(newNumber);
            }
        }
        System.out.println("Imperative Result: " + result);
    }
}
