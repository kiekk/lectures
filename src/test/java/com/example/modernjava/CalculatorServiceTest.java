package com.example.modernjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}