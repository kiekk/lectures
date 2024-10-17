package com.fastcampus.shyoon.part3;

import com.fastcampus.shyoon.part3.service.RankingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class SimpleTest {

    @Autowired
    private RankingService rankingService;

    @Test
    void getRanks() {
        // redis warm up 용
        // redis 첫 실행시에는 네트워크 통신으로 인해 조금 시간이 소요될 수 있다.
        rankingService.getTopRankUser(1);

        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());
        System.out.println((elapsed.getNano() / 1_000_000) + "ms"); // 0~1ms

        before = Instant.now();
        List<String> topRankUser = rankingService.getTopRankUser(10);
        elapsed = Duration.between(before, Instant.now());
        System.out.println((elapsed.getNano() / 1_000_000) + "ms"); // 0~1ms
    }

    @Test
    void insertScore() {
        for (int i = 0; i < 1_000_000; i++) {
            int score = (int) (Math.random() * 1_000_000); // 0 ~ 999,999
            String userId = "user_" + i;

            rankingService.setUserScore(userId, score);
        }
    }

    @Test
    void inMemorySortPerformance() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            int score = (int) (Math.random() * 1_000_000); // 0 ~ 999,999
            list.add(score);
        }

        Instant before = Instant.now();
        Collections.sort(list); // nlogn 시간 복잡도
        Duration elapsed = Duration.between(before, Instant.now());

        System.out.println((elapsed.getNano() / 1_000_000) + "ms"); // 약 250ms
    }
}
