package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JUnitCalculatorTest {


    private lateinit var calculator: Calculator

    @BeforeEach
    fun beforeEach() {
        // given
        calculator = Calculator(5)
    }

    @Test
    fun addTest() {
        // when
        calculator.add(3)

        // then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minusTest() {
        // when
        calculator.minus(2)

        // then
        assertThat(calculator.number).isEqualTo(3)
    }

    @Test
    fun multiplyTest() {
        // when
        calculator.multiply(3)

        // then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divideTestGivenZero() {
        // when
        // then
        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }
    }

    @Test
    fun divideTestGivenNotZero() {
        // when
        calculator.divide(2)

        // then
        assertThat(calculator.number).isEqualTo(2)
    }

}