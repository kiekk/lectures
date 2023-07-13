/**
 * 타입 좁히기
 * 조건문 등을 이용해 넓은타입에서 좁은타입으로
 * 타입을 상황에 따라 좁히는 방법
 */

type Person = {
    name: string
    age: number
}

// value → number : toFixed
// value → string : toUpperCase
// value → Date : getTime
// value → Person : name은 age살 입니다.
function func(value: number | string | Date | null | Person) {
    if (typeof value === 'number') {
        console.log(value.toFixed())
    } else if (typeof value === 'string') {
        console.log(value.toUpperCase())
        // 객체의 경우 typeof 키워드로는 한계가 있기 때문에 instanceof 키워드를 사용합니다.
    } else if (value instanceof Date) {
        console.log(value.getTime())
    } else if(value && 'age' in value) {
        console.log(`${value.name}은 ${value.age}살 입니다.`)
    } else {
        console.log('value is null')
    }
}