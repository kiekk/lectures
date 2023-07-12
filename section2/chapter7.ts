// void
// void는 아무런 값도 없음을 의미하는 타입입니다.
// 보통 아무런 값도 반환하지 않는 함수의 타입을 정의할 때 사용합니다.

function func1(): string {
    return 'hello'
}

function func2(): void {
    console.log('hello')
}

let a: void
// a = 1 // 오류
// a = 'hello' // 오류
// a = {} // 오류
// undefined 만 할당 가능
a = undefined
// null의 경우 'strictNullChecks' 옵션의 값에 따라 null 할당 가능/불가능이 결정됩니다.
a = null

// void 타입의 존재 이유?

// 함수의 반환 타입을 undefined로 설정할 경우 반드시 함수는 null | undefined를 반환해야 합니다.
// function func3(): undefined { // 오류 발생!
//     console.log("hello");
// }

// function func4(): null {
//     console.log("hello");
//     return null;
// }

// 정상 실행
function func5(): void {
    console.log("hello");
}