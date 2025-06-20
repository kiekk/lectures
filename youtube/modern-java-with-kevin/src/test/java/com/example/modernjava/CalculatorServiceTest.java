package com.example.modernjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorServiceTest {

    @Test
    public void testCalculateAddition() {
        Calculation calculation = new Addition();
        int additionResult = calculation.calculate(1, 1);

        assertEquals(additionResult, 2);
    }

    @Test
    public void testCalculateSubtraction() {
        Calculation calculation = new Subtraction();
        int additionResult = calculation.calculate(1, 1);

        assertEquals(additionResult, 0);
    }

    @Test
    public void testCalculateMultiplication() {
        Calculation calculation = new Multiplication();
        int additionResult = calculation.calculate(1, 1);

        assertEquals(additionResult, 1);
    }

    @Test
    public void testCalculateDivision() {
        Calculation calculation = new Division();
        int additionResult = calculation.calculate(9, 3);

        assertEquals(additionResult, 3);
    }

    @Test
    public void testCalculateDivisionFailed() {
        // 0 으로 나누면 에러 발생
        Calculation calculation = new Division();
        assertThrows(ArithmeticException.class, () -> calculation.calculate(9, 0));
    }

}