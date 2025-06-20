/**
 *
 */

// Error 발생
// type Person = {
//     name: string;
// };

// type Person = { ❌
//     age: number;
// };

// 인터페이스는 선언 합침(Declaration Merging)이 가능
interface Person {
    name: string;
}

interface Person {
    age: number;
}

const person: Person = {
    name: 'soono',
    age: 10,
};

// 주의할 점
// 만약 다음과 같이 동일한 이름의 인터페이스들이 동일한 이름의 프로퍼티를 서로 다른 타입으로 정의한다면 오류가 발생합니다.

interface Person {
    name: string;
}

interface Person {
    // name: number; // ❌
    age: number;
}

/*
    첫번째 Person에서는 name 프로퍼티의 타입을 string으로 두번째 Person에서는 name 프로퍼티의 타입을 number 타입으로 정의했습니다.
    이렇게 동일한 프로퍼티의 타입을 다르게 정의한 상황을 '충돌'이라고 표현하며 선언 합침에서 이러한 충돌은 허용되지 않습니다.
 */