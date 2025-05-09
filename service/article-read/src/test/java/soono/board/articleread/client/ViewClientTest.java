package soono.board.articleread.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

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
}