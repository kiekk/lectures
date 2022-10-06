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
//        final int size = numbers.size();
//        for (int i = 0; i < size; i++) {
//            builder.append(numbers.get(i));
//            if (i != size - 1) {
//                builder.append(" : ");
//            }
//        }
        // 단점
        // 1. index 로 numbers 에 접근하기 때문에 index 를 잘 못 설정할 경우 ArrayIndexOutOfBoundsException 발생합니다.
        // 2. if 문이 어떤 의도인지 파악하기 쉽지 않습니다.

        // 3. 1번에서 사용한 forEach 에서 : 제거
        for (Integer number : numbers) {
            builder.append(number).append(" : ");
        }

        // 마지막에 builder 를 체크하여 데이터가 있을 경우 마지막에 있는 " : " 를 제거
        if (builder.length() > 0) {
            builder.delete(builder.length() - 3, builder.length());
        }

        // 단점
        // 1. if 문이 어떤 의도인지 파악하기 쉽지 않습니다.
        // 2. builder.length() - 3 에서 3이 어떤 의도인지 파악하기 쉽지 않습니다.
        // 3. " : " 를 의미하는 3인데, " : " 이 " :  -" 과 같이 변경되면 3도 변경해줘야 합니다.

        System.out.println(builder);
    }
}
