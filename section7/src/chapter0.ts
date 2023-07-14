/**
 * 제네릭
 */

function func(value: string) {
    return value
}

// func(10) // number 형식은 오류 발생
// func(true) // boolean 형식은 오류 발생

function func2(value: any) {
    return value
}

// func2의 매개변수 타입을 any 타입으로 설정하면 오류는 발생하지 않지만,
// 반환값이 전부 any 타입
// 각각의 타입에 맞게끔 반환 타입을 설정하고 싶은 경우 제네릭을 사용해야 함
let num = func2(10)
// num.toUpperCase() // 컴파일 시 오류는 아니지만, 런타임 오류 발생
let bool = func2(true)
let str = func2('string')

// 제네릭 함수
function func3<T>(value: T): T {
    return value
}

let num2 = func3(10)
let num3 = func3<number>(10)
let bool2 = func3(true)
let bool3 = func3<boolean>(true)
let str2 = func3('string')
let str3 = func3<string>('string')


