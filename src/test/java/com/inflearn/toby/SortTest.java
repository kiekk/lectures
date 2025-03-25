package com.inflearn.toby;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTest {

    @Test
    @DisplayName("문자열 목록을 길이 순으로 정렬, 2개의 문자열로 테스트")
    void sort() {
        // given
        Sort sort = new Sort();

        // when
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // then
        assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    @DisplayName("문자열 목록을 길이 순으로 정렬, 3개의 문자열로 테스트")
    void sort3Items() {
        // given
        Sort sort = new Sort();

        // when
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        // then
        assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }

    @Test
    @DisplayName("문자열 목록을 길이 순으로 정렬, 이미 정렬이 된 목록으로 테스트")
    void sortAlreadySorted() {
        // given
        Sort sort = new Sort();

        // when
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        // then
        assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));
    }
}
