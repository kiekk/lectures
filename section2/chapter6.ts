// any
// 특정 변수의 타입을 확실하게 알지 못할때

// let anyVar = 10
// anyvar = 'hello  // 'string' 형식은 'number' 형식에 할당할 수 없습니다.

// any 타입은 다른 타입의 값을 할당할 수 있습니다.
// let anyVar: any = 10
// anyVar = 'hello'

// anyVar = true
// anyVar = {}
// anyVar = () => {}

// anyVar.toUpperCase() // string 타입의 메서드 호출 가능
// anyVar.toFixed() // number 타입의 메서드 호출 가능


// 모든 타입의 변수에 값을 할당할 수 있습니다.
// let num: number = 10
// num = anyVar

// 대신 타입을 잘못 사용한다면 런타임시 오류가 발생합니다.


// unknown
// any 타입과 비슷하지만 any 타입 보다는 안정적인 타입입니다.
let unknownVar: unknown
unknownVar = ""
unknownVar = 1
unknownVar = () => {
}

// 하지만 any 타입과 달리 unknown 타입의 값은 모든 타입의 변수에 할당할 수 없습니다.

let num: number = 10
// num = unknownVar // 오류
// unknownVar.toUpperCase() // 오류
// unknownVar.toFixed() // 오류

// 위 문법을 정상적으로 실행하고 싶다면
// 변수의 타입을 체크해야 합니다.
if (typeof unknownVar === 'number') {
    num = unknownVar
    unknownVar.toFixed()
}

if (typeof unknownVar === 'string') {
    unknownVar.toUpperCase()
}


