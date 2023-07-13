/**
 * 인터페이스
 */

interface Person {
    readonly name: string // 읽기 전용 프로퍼티
    age?: number // 선택적 파라미터
    // sayHi: () => void // 함수 타입 표현식
    // sayHi(): void // 호출 시그니처
}

const person: Person = {
    name: 'soono',
    age: 10,
}

// person.name = '홍길동' // 오류, 읽기 전용 프로퍼티는 변경 불가

// 함수 타입 표현식은 메서드 오버로딩 불가능

interface Person2 {
    readonly name: string
    age?: number
    sayHi: () => void
    // sayHi: (a: number, b: number) => void // ❌
}

// 호출 시그니처는 메서드 오버로딩 가능
interface Person3 {
    readonly name: string
    age?: number
    sayHi(): void
    sayHi(a: number): void
    sayHi(a: number, b: number): void
}

// 타입 별칭은 Union, Intersection 타입 정의가 가능하지만
// 인터페이스는 불가능합니다.
type Type1 = number | string;
type Type2 = number & string;

interface Person4 {
    name: string
    age: number
} // | number // ❌

// 인터페이스로 만든 타입을 Union, Intersection 타입으로 사용하려면
// 타입 별칭과 같이 사용하거나 타입 주석에서 직접 사용해야 합니다.
type Type3 = number | string | Person4;
type Type4 = number & string & Person4;

const person2: Person4 | string = {
    name: 'soono',
    age: 10,
};