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
}