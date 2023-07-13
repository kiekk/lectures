/**
 * 기본 타입간의 호환성
 */

let num1: number = 10
let num2: 10 = 10

// number literal 타입을 number 타입에 할당 가능
num1 = num2

/**
 * 객체 타입간의 호환성
 * → 어떤 객체 타입을 다른 객체 타입으로 취급해도 괜찮은가?
 */

type Animal = {
    name: string
    color: string
}

type Dog = {
    name: string
    color: string
    breed: string
}

let animal: Animal = {
    name: '기린',
    color: 'yello'
}

let dog: Dog = {
    name: '돌돌이',
    color: 'brown',
    breed: '진도'
}

animal = dog

// 객체 타입에도 관계(슈퍼타입,서브타입)이 존재한다.
// Dog → Animal: 업 캐스팅
// Animal → Dog : 다운 캐스팅
// Animal 타입에는 breed 속성이 없기 때문에 Dog 타입에 할당 불가능 (다운 캐스팅 ❌)
// dog = animal // 오류

type Book = {
    name: string
    price: number
}

type ProgrammingBook = {
    name: string
    price: number
    skill: string
}

// Book = 슈퍼 타입
// ProgrammingBook = 서브 타입
let book: Book
let programmingBook: ProgrammingBook = {
    name: '한 입 크기로 잘라먹는 리액트',
    price: 33_000,
    skill: 'reactjs'
}

// 업 캐스팅 가능
book = programmingBook
// 다운 캐스팅 불가능
// programmingBook = book // 오류

// 객체 리터럴 방식에서는 초과 프로퍼티 검사
let book2: Book = {
    name: '한 입 크기로 잘라먹는 리액트',
    price: 33_000,
    // skill: 'reactjs' // 오류
}

// 객체 리터럴 방식이 아니면 초과 프로퍼티 검사 ❌
let book3: Book = programmingBook

function func(book: Book) {
}

// 초과 프로퍼티 검사
func({
    name: '한 입 크기로 잘라먹는 리액트',
    price: 33_000,
    // skill: 'reactjs' // 오류
})
// 초과 프로퍼티 검사 ❌
func(programmingBook)