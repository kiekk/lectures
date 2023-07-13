/**
 * 인터페이스 확장
 */

/*

// Bad ❌
// name, age property가 계속 중복

interface Animal {
    name: string
    age: number
}

interface Dog {
    name: string
    age: number
    isBark: boolean
}

interface Cat {
    name: string
    age: number
    isScratch: boolean
}

interface Chicken {
    name: string
    age: number
    isFly: boolean
}
 */

// Good ✅`
interface Animal {
    name: string
    age: number
}

interface Dog extends Animal {
    isBark: boolean
}

interface Cat extends Animal {
    isScratch: boolean
}

interface Chicken extends Animal {
    name: 'chicken', // 타입 재정의
    isFly: boolean
}

// 프로퍼티 타입 재정의시 원본 타입의 서브타입이 되어야 합니다.
// 즉, A 프로퍼티를 재정의한 타입이 B라고 하면 A가 B의 슈퍼 타입이 되도록 정의해야 합니다.

/*

// Bad ❌
interface Animal {
  name: string;
  color: string;
}

interface Dog extends Animal {
  name: number; // ❌
  breed: string;
}
 */

// 타입 별칭 확장
type Animal2 = {
    name: string
    color: string
}

interface Dog2 extends Animal2 {
    breed: string
}


// 다중 확장
interface DogCat extends Dog, Cat {
}

const dogCat: DogCat = {
    name: "",
    age: 0,
    isBark: true,
    isScratch: true,
}