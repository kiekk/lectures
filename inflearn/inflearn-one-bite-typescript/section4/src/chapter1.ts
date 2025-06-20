/**
 * 함수 타입 표현식
 */

// 비슷한 형식의 함수를 만들어야 할 때 유용
type Operation = (a: number, b: number) => number

const add: Operation = (a, b) => a + b
const sub: Operation = (a, b) => a - b
const multiply: Operation = (a, b) => a * b
const divide: Operation = (a, b) => a / b

// 함수 타입 리터럴
const add2: (a: number, b: number) => number = (a, b) => a + b

/**
 * 호출 시그니처
 * (콜 시그니처)
 */

// 함수 타입 정의
type Operation2 = {
    (a: number, b: number): number
}

const add3: Operation2 = (a, b) => a + b
const sub2: Operation2 = (a, b) => a - b
const multiply2: Operation2 = (a, b) => a * b
const divide2: Operation2 = (a, b) => a / b

// 하이브리드 타입
type Operation3 = {
    (a: number, b: number): number
    name: string
}

const add4: Operation3 = (a, b) => a + b
add4(1, 2)
add.name
