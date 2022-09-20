package com.tobyspring.studytobyspring.template;

import com.tobyspring.studytobyspring.dao.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CalcSumTest {

    Calculator calculator;
    String numFilePath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = "C:\\study\\study-toby-spring\\src\\main\\resources\\numbers.txt";
    }

    @Test
    public void sumOfNumbers() throws IOException {
        int sum = calculator.calcSum(this.numFilePath);
        assertThat(sum).isEqualTo(10);
    }

    /*
    템플릿/콜백을 미리 적용해놓았기 때문에 쉽게 새로운 기능을 추가할 수 있습니다.
     */
    @Test
    public void multiplyOfNumbers() throws IOException {
        Integer multiply = calculator.calcMultiply(this.numFilePath);
        assertThat(multiply).isEqualTo(24);
    }

    @Test
    public void concatenateStrings() throws IOException {
        String concatenate = calculator.concatenate(this.numFilePath);
        assertThat(concatenate).isEqualTo("1234");
    }
}
