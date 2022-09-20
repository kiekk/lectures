package com.tobyspring.studytobyspring.template;

import com.tobyspring.studytobyspring.dao.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        int sum = calculator.calcSum("C:\\study\\study-toby-spring\\src\\main\\resources\\numbers.txt");


        assertThat(sum).isEqualTo(10);
    }
}
