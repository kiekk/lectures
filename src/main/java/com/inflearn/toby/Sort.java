package com.inflearn.toby;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {
    public static void main(String[] args) {
        List<String> languages = Arrays.asList("java", "kotlin", "javascript", "typescript", "go", "python");
        // 전략 패턴
        // 알고리즘 구현체를 외부에서 주입
        Collections.sort(languages, Comparator.comparingInt(String::length));
        languages.forEach(System.out::println);
    }
}
