/**
 * 타입 추론
 */

let a = 10
// number 타입으로 추론

let b = 'hello'
// string 타입으로 추론

let c = {
    id: 1,
    name: 'soonho',
    profile: {
        nickname: 'soono'
    }
}

let {id, name, profile} = c

let [one, two, three] = [1, 'hello', true]

/*
    타입 추론이 가능한 상황
    1. 변수 서언
    2. 구조 분해 할당
    3. 함수의 반환값
    4. 기본값이 설정된 매개변수
 */
function func(message = 'hello') {
    return 'hello'
}

/*
    주의해야할 상황
    1. 초기값이 없는 변수
    2. const 상수
 */

let d // any 타입으로 추론

d = 10 // 이제 number 타입으로 추론
d.toFixed()

d = 'hello' // 이제 string 타입으로 추론
d.toUpperCase()
// d.toFixed() // 오류, 현재 d는 string 으로 추론되기 때문에 number 타입의 메서드 호출 불가능

const num = 10 // 상수의 경우 literal 타입으로 타입 추론
const str = 'hello'

let arr = [1, 'string']
// 배열의 경우 string과 number의 union 타입으로 추론됩니다.
