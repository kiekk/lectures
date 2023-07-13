/**
 * 타입 단언
 */

type Person = {
    name: string
    age: number
}

let person = {} as Person
person.name = ''
person.age = 0

type Dog = {
    name: string
    color: string
}

let dog: Dog = {
    name: '돌돌이',
    color: 'brown',
    breed: '진도'
} as Dog

/**
 * 타입 단언의 규칙
 * 값 as 단언 ← 단언ㅇ식
 * A as B
 * A가 B의 슈퍼타입이거나
 * A가 B의 서브타입이어야 함
 */

let num1 = 10 as never
let num2 = 10 as unknown
// let num3 = 10 as string
let num4 = 10 as unknown as string // 에러는 발생하지 않지만 추천하지 않는 방식

/**
 * const 단언
 */

let num5 = 10 as const
let cat = {
    name: '야옹이',
    color: 'yellow'
} as const

// cat.name = '' // 읽기 전용이므로 값 재할당 불가능

/**
 * Non Null 단언
 */

type Post = {
    title: string
    author?: string
}

let post: Post = {
    title: '게시글1',
    author: 'soonho'
}

const len: number = post.author!.length
