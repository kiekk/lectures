/**
 * 함수 타입 호환성
 * 특정 함수 타입을 다른 함수 타입으로 취급해도 괜찮은가?
 * 1. 반환갑의 타입이 호환되는가
 * 2. 매개변수의 타입이 호환되는가
 */

// 기준1. 반환값이 호환되는가
type A = () => number
type B = () => 10

let a: A = () => 10
let b: B = () => 10

a = b // ✅, 10을 number 타입에 할당 가능, 업 캐스팅
// b = a // ❌, number를 10에 할당 불가능, 다운 캐스팅

// 기준2. 매개변수가 호환되는가
// 2-1. 매개변수의 개수가 같을 때

type C = (value: number) => void
type D = (value: number) => void

let c: C = (value) => {
}
let d: D = (value) => {
}

c = d // ✅
d = c // ✅

type Animal = {
    name: string
}

type Dog = {
    name: string
    color: string
}

let animalFunc = (animal: Animal) => {
    console.log(animal.name)
}
let dogFunc = (dog: Dog) => {
    console.log(dog.name)
    console.log(dog.color)
}

dogFunc = animalFunc // ✅ 다운 캐스팅 가능
// animalFunc = dogFunc // ❌, 함수 타입의 관계에서는 업 캐스팅 불가능

// 2-2. 매개변수의 개수가 다를 때

type Func1 = (a: number, b: number) => void
type Func2 = (a: number) => void

let func1: Func1 = (a, b) => {
}
let func2: Func2 = (a) => {
}

func1 = func2 // ✅ 대입하려는 타입의 매개변수의 개수가 더 적은 경우에 호환 가능
// func2 = func1 // ❌

