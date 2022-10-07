package com.example.modernjava;

public class OopAnotherExample {

    public static void main(String[] args) {
        CalculatorService calculatorService = new CalculatorService();
        int result = calculatorService.calculate(1, 1);

        System.out.println(result);
    }
}

class CalculatorService {
    public int calculate(int num1, int num2) {
        return num1 + num2;
    }
}
