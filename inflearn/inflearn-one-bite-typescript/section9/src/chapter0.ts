/**
 * 조건부 타입
 */

// 조건식의 결과 따라 타입을 정의
type A = number extends string ? string : number // number

type ObjA = {
    a: number
}

type ObjB = {
    a: number
    b: number
}

type B = ObjB extends ObjA ? number : string // number

/**
 * 제네릭과 조건부 타입
 */

type StringNumberSwitch<T> = T extends number ? string : number

let varA: StringNumberSwitch<number> // string
let varB: StringNumberSwitch<string> // number

function removeSpaces<T>(text: T): T extends string ? string : undefined;
function removeSpaces(text: any) {
    if (typeof text === "string") {
        return text.replaceAll(" ", "")
    } else {
        return undefined
    }
}

let result = removeSpaces("hello my world") // string

let result2 = removeSpaces(undefined) // undefined