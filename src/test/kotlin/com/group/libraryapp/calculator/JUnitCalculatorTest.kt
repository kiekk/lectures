package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JUnitCalculatorTest {

    @Test
    fun addTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(2)

        // then
        assertThat(calculator.number).isEqualTo(3)
    }

    @Test
    fun multiplyTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTestGivenZero() {
        // given
        val calculator = Calculator(5)

        // when
        // then
        val message = assertThrows<IllegalArgumentException> { calculator.divide(0) }.message

        assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")

    }

    @Test
    fun divideTestGivenNotZero() {
        // given
        val calculator = Calculator(10)

        // when
        calculator.divide(2)

        // then
        assertThat(calculator.number).isEqualTo(5)
    }

}