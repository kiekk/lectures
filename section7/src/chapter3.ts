/**
 * 제네릭 인터페이스
 */

interface KeyPair<K, V> {
    key: K
    value: V
}

const keyPair: KeyPair<string, number> = {
    key: 'key',
    value: 0
}

const keyPair2: KeyPair<boolean, string[]> = {
    key: true,
    value: ['1']
}


/**
 * 인덱스 시그니처
 */

interface NumberMap {
    [key: string]: number
}

const numberMap1: NumberMap = {
    key: -1231,
    key2: 123
}

interface Map<V> {
    [key: string]: V
}

const stringMap: Map<string> = {
    key: 'value',
    // key2: 1234 // ❌
}

const booleanMap: Map<boolean> = {
    key: true
}

/**
 * 제네릭 타입 별칭
 */

type Map2<V> = {
    [key: string]: V
}

const stringMap2: Map2<string> = {
    key: 'value'
}

/**
 * 제네릭 인터페이스 활용 예시
 * → 유저 관리 프로그램
 * → 유저 구분 : 학생 유저 / 개발자 유저
 */

interface Student {
    type: 'student',
    school: string
}

interface Developer {
    type: 'developer',
    skill: string
}

interface User {
    name: string
    profile: Student | Developer
}

function goToSchool(user: User) {
    if (user.profile.type !== 'student') {
        console.log('잘 못 오셨습니다.')
        return
    }

    const school = user.profile.school
    console.log(`${school}로 등교 완료`)
}

const developerUser: User = {
    name: 'soono',
    profile: {
        type: 'developer',
        skill: 'TypeScript'
    }
}

const studentUser: User = {
    name: '홍길동',
    profile: {
        type: 'student',
        school: 'school'
    }
}


// 제네릭으로 개선
interface User2<T> {
    name: string
    profile: T
}

// 제네릭을 사용해 이 함수는 User<Student> 타입만 호출가능하므로 타입 좁히기 필요 ❌
function goToSchool2(user: User2<Student>) {
    const school = user.profile.school
    console.log(`${school}로 등교 완료`)
}

const developerUser2: User2<Developer> = {
    name: 'soono',
    profile: {
        type: 'developer',
        skill: 'TypeScript'
    }
}

const studentUser2: User2<Student> = {
    name: '홍길동',
    profile: {
        type: 'student',
        school: 'school'
    }
}