/**
 * keyof
 * 객체 타입에 적용하는 연산자
 */

interface Person {
    name: string
    age: number
}

function getPropertyKey(person: Person, key: keyof Person) {
    return person[key]
}

const person: Person = {
    name: "soono",
    age: 10,
}


// typeof와 keyof 함께 사용하기
type Person2 = typeof person;
// 결과
// {name: string, age: number, location:string}

/*
    1. typeof person은 {name: string, age: number, location:string} 이다.
    2. keyof (typeof person)은 keyof ({name: string, age: number, location:string})이다.
    3. 따라서 keyof typeof person은 (name | age | location)이다.
 */
function getPropertyKey2(person: Person, key: keyof typeof person) {
    return person[key];
}

const person2: Person2 = {
    name: "soono",
    age: 10,
};