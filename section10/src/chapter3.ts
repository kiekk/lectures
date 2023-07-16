/**
 * Exclude<T, U>
 * → 제외하다, 추방하다
 * → T에서 U를 제거하는 타입
 */

type A = Exclude<string | boolean, boolean> // string
/*
1단계
Exclude<string, boolean>
Exclude<boolean, boolean>

2단계
string
never

string | never union 타입이지만
union에서 never는 제외되기 때문에
최종적으로는 string 타입만 남게됩니다.
 */

// Exclude<T, U> 직접 구현
type Exclude<T, U> = T extends U ? never : T

/**
 * Extract<T, U>
 * → T에서 U를 추출하는 타입
 */

type B = Extract<string | boolean, boolean> // boolean

type Extract<T, U> = T extends U ? T : never


/**
 * ReturnType
 * → 함수의 반환값 타입을 추출하는 타입
 */

function funcA() {
    return 'hello'
}

function funcA2(): string {
    return 'hello'
}

function funcB() {
    return 10
}

function funcB2(): number {
    return 10
}

type ReturnA = ReturnType<typeof funcA> // string literal type = 'hello'
type ReturnA2 = ReturnType<typeof funcA2> // string
type ReturnB = ReturnType<typeof funcB> // number literal type = 10
type ReturnB2 = ReturnType<typeof funcB2> // number

// ReturnType 직접 구현
type ReturnType<T extends (...args: any) => any> = T extends (...args: any) => infer R ? R : never