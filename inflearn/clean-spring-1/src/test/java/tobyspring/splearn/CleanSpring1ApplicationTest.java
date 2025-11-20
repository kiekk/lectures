package tobyspring.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class CleanSpring1ApplicationTest {
    @Test
    void run() {
        MockedStatic<SpringApplication> mock = Mockito.mockStatic(SpringApplication.class);

        CleanSpring1Application.main(new String[]{});

        mock.verify(() -> SpringApplication.run(CleanSpring1Application.class, new String[]{}));
    }
}