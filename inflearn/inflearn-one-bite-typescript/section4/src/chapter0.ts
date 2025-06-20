/**
 * 함수 타입
 */

function func(a: number, b: number): number {
    return a + b
}

/**
 * 화살표 함수
 */

const add = (a: number, b: number): number => a + b

/**
 * 함수의 매개변수
 */

function introduce(name = 'soono', tall?: number): void {
    console.log(`name : ${name}, tall : ${tall}`)

    // 타입 가드
    if (typeof tall === 'number') {
        console.log(`tail : ${tall + 10}`)
    }
}

introduce('soonho', 170)
introduce('soonho')
introduce()

// rest parameter
function getSum(...rest: number[]): number {
    return Array.from(rest).reduce((previousValue, currentValue) => previousValue + currentValue, 0)
}

getSum(1, 2, 3) // 6
getSum(1, 2, 3, 4, 5) // 15