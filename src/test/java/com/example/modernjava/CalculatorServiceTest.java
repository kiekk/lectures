package com.example.modernjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorServiceTest {

    @Test
    public void testCalculateAddition() {
        CalculatorService calculatorService = new CalculatorService();
        int additionResult = calculatorService.calculate('+', 1, 1);

        assertEquals(additionResult, 2);
    }

    @Test
    public void testCalculateSubtraction() {
        CalculatorService calculatorService = new CalculatorService();
        int additionResult = calculatorService.calculate('-', 1, 1);

        assertEquals(additionResult, 0);
    }

    @Test
    public void testCalculateMultiplication() {
        CalculatorService calculatorService = new CalculatorService();
        int additionResult = calculatorService.calculate('*', 1, 1);

        assertEquals(additionResult, 1);
    }

    @Test
    public void testCalculateDivision() {
        CalculatorService calculatorService = new CalculatorService();
        int additionResult = calculatorService.calculate('/', 9, 3);

        assertEquals(additionResult, 3);
    }

    @Test
    public void testCalculateDivisionFailed() {
        // 0 으로 나누면 에러 발생
        CalculatorService calculatorService = new CalculatorService();
        assertThrows(ArithmeticException.class, () -> calculatorService.calculate('/', 9, 0));
    }

}