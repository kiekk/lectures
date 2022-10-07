package com.example.modernjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FpCalculatorServiceTest {

    @Test
    public void testFpCalculateAddition() {
        FpCalculatorService fpCalculatorService = new FpCalculatorService();
        int additionResult = fpCalculatorService.calculate(new Addition(), 11, 10);

        assertEquals(additionResult, 21);
    }

    @Test
    public void testFpCalculateSubtraction() {
        FpCalculatorService fpCalculatorService = new FpCalculatorService();
        int additionResult = fpCalculatorService.calculate(new Subtraction(), 11, 10);

        assertEquals(additionResult, 1);
    }

    @Test
    public void testFpCalculateMultiplication() {
        FpCalculatorService fpCalculatorService = new FpCalculatorService();
        int additionResult = fpCalculatorService.calculate(new Multiplication(), 11, 10);

        assertEquals(additionResult, 110);
    }

    @Test
    public void testFpCalculateDivision() {
        FpCalculatorService fpCalculatorService = new FpCalculatorService();
        int additionResult = fpCalculatorService.calculate(new Division(), 11, 10);

        assertEquals(additionResult, 1);
    }

}