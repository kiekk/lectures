/**
 * 분산적인 조건부 타입
 */

type StringNumberSwitch<T> = T extends number ? string : number;

let c: StringNumberSwitch<number | string> // string | number

// exclude
type Exclude<T, U> = T extends U ? never : T

type A = Exclude<number | string | boolean, string> // number | boolean

// extract
type Extract<T, U> = T extends U ? T : never

type B = Extract<number | string | boolean, string> // string