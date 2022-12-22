package com.group.libraryapp.calculator

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest();
    calculatorTest.minusTest();
    calculatorTest.multiplyTest();
    calculatorTest.divideTestGivenZero();
    calculatorTest.divideTestGivenNotZero()
}

class CalculatorTest {

    fun addTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        if (calculator.number != 8) {
            throw IllegalStateException()
        }
    }

    fun minusTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(4)

        // then
        if (calculator.number != 1) {
            throw IllegalStateException()
        }
    }

    fun multiplyTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        if (calculator.number != 15) {
            throw IllegalStateException()
        }
    }

    fun divideTestGivenZero() {
        // given
        val calculator = Calculator(5)

        // when
        // then
        try {
            calculator.divide(0)
        } catch (e: IllegalArgumentException) {
            // 테스트 실패
            if (e.message != "0으로 나눌 수 없습니다.") {
                throw IllegalStateException()
            }
            // 테스트 성공
            return
        } catch (e: Exception) {
            // 테스트 실패
            throw IllegalStateException()
        }
        // 테스트 실패
        throw IllegalStateException()
    }

    fun divideTestGivenNotZero() {
        // given
        val calculator = Calculator(10)

        // when
        calculator.divide(2)

        // then
        if (calculator.number != 5) {
            throw IllegalStateException()
        }
    }

}