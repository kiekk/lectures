package soono.board.hotarticle.utils;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
class TimeCalculatorUtilTest {

    @Test
    void test() {
        Duration duration = TimeCalculatorUtil.calculateDurationToMidnight();
        // 자정까지 남은 시간 (분 단위)
        log.info("duration.getSeconds() / 60 = {}", duration.getSeconds() / 60);
    }

}
