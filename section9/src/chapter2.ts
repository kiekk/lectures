/**
 * infer
 */

type ReturnType<T> = T extends () => infer R ? R : never

type FuncA = () => string
type FuncB = () => number

type A = ReturnType<FuncA> // string
type B = ReturnType<FuncB> // number