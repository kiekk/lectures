// object
let user: object = {
    id: 1,
    name: 'soonho',
}

// object 형식에 id 속성이 없다는 에러가 발생
// user.id // S2339: Property 'id' does not exist on type 'object'.


// 객체 리터럴 타입
let user2: {
    id: number
    name: string
} = {
    id: 2,
    name: 'soonho'
}

user2.id


// optional property
let user3: {
    id: number;
    name: string;
}

// TS2741: Property 'id' is missing in type '{ name: string; }' but required in type '{ id: number; name: string; }'.
user3 = {
    name: "홍길동", // 오류 발생!
};

let user4: {
    id?: number; // 선택적 프로퍼티가 된 id
    name: string;
}

user4 = {
    name: "홍길동",
};

// readonly property
let user5: {
    id?: number;
    readonly name: string; // name은 이제 Readonly 프로퍼티가 되었음
} = {
    id: 1,
    name: "soonho",
};

// TS2540: Cannot assign to 'name' because it is a read-only property.
user5.name = "dskfd"; // 오류 발생