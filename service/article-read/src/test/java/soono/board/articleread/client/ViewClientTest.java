package soono.board.articleread.client;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class ViewClientTest {
    @Autowired
    ViewClient viewClient;

    @Test
    void readCacheableTest() throws InterruptedException {
        viewClient.count(1L); // 로그 출력 (cache miss)
        viewClient.count(1L); // 로그 출력 안됨 (cache hit)
        viewClient.count(1L); // 로그 출력 안됨 (cache hit)

        TimeUnit.SECONDS.sleep(3);
        viewClient.count(1L); // 로그 출력 (cache miss)
    }

    @Test
    void readCacheableMultiThreadTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        viewClient.count(1L); // init cache

        for (int i = 0; i < 5; i++) {
            CountDownLatch latch = new CountDownLatch(5);
            for (int j = 0; j < 5; j++) {
                executorService.submit(() -> {
                    viewClient.count(1L); // cache hit
                    latch.countDown();
                });
            }
            latch.await();
            TimeUnit.SECONDS.sleep(3);
            log.info("--cache expired--");
        }
    }
}