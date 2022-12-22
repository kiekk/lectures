package com.group.libraryapp.calculator

class Calculator(
    private var _number: Int
) {

    // number 에 접근할 수 있도록 getter 를 작성
    val number: Int
        get() = this._number

    fun add(operand: Int) {
        this._number += operand
    }

    fun minus(operand: Int) {
        this._number -= operand
    }

    fun multiply(operand: Int) {
        this._number *= operand
    }

    fun divide(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        }
        this._number /= operand
    }

}