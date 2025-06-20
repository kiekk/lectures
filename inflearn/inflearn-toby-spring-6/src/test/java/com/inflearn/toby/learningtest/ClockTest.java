package com.inflearn.toby.learningtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

class ClockTest {
    // Clock을 이용해서 LocalDateTime.now()를 사용하는 코드를 테스트하는 방법
    @Test
    void clock() {
        Clock clock = Clock.systemDefaultZone();
        LocalDateTime dateTime1 = LocalDateTime.now(clock);
        LocalDateTime dateTime2 = LocalDateTime.now(clock);

        Assertions.assertThat(dateTime2).isAfter(dateTime1);
    }

    // Clock을 Test에서 사용할 때 내가 원하는 시가능ㄹ 지정해서 현재 시간을 가져올 수 있는지
    @Test
    void fixedClock() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        LocalDateTime dateTime1 = LocalDateTime.now(clock);
        LocalDateTime dateTime2 = LocalDateTime.now(clock);

        Assertions.assertThat(dateTime2).isEqualTo(dateTime1);
    }
}
