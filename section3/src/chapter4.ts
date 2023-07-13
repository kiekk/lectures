/**
 * 대수 타입
 * → 여러 개의 타입을 합성해서 새롭게 만들어낸 타입
 * → 합집합, 교집합 타입이 존재
 */

/**
 * 1. 합집합 → Union 타입
 */

// a 변수에는 string 또는 number 타입의 값을 할당할 수 있습니다.
let a: string | number
a = 1
a = 'string'

let arr: (number | string | boolean) [] = [1, 'hello', true]

type Dog = {
    name: string
    color: string
}

type Person = {
    name: string
    language: string
}

// 객체 Union 타입
type Union1 = Dog | Person

let union1: Union1 = {
    name: '',
    color: ''
}

let union2: Union1 = {
    name: '',
    language: ''
}

let union3: Union1 = {
    name: '',
    color: '',
    language: ''
}

// TS2322: Type '{ name: string; }' is not assignable to type 'Union1'. Property 'language' is missing in type '{ name: string; }' but required in type 'Person'
// let union4: Union1 = {
//     name: ''
// }

/**
 * 2. 교집합 → Intersection 타입
 */

// a 변수에는 string 이면서 number 타입의 값을 할당할 수 있습니다.
// 하지만 string 이면서 동시에 number 타입은 없으므로 variable은 never 타입이 됩니다.
// let variable: never
let variable: number & string

// Dog와 Person의 교집합
type Intersection = Dog & Person

let intersection1: Intersection = {
    name: '',
    color: '',
    language: ''
}