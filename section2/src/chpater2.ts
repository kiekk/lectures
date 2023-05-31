// array
let numArr: number[] = [1, 2, 3]

let strArr: string[] = ["hello", "im", "winterlood"];

let boolArr: Array<boolean> = [true, false, true];

// number[]와 Array<number>는 동일합니다.

// nested array
let doubleArr : number[][] = [
    [1, 2, 3],
    [4, 5],
];

// 다양한 타입을 갖는 배열
let multiArr = [1, "hello"];

// 유니온 타입이라고도 합니다.
let multiArr2: (number | string)[] = [1, "hello"];

let multiArr3: (number | string)[]

// 단점, 타입의 순서를 지정할 수는 없습니다.
multiArr3 = [1, "hello"]
multiArr3 = ["hello", 1]



// tuple
let tup1: [number, number] = [1, 2];

// tup1 = [1, 2, 3]
//TS2322: Type '[number, number, number]' is not assignable to type '[number, number]'.
// Source has 3 element(s) but target allows only 2

let tup2: [number, string, boolean] = [1, "hello", true];

let tup3: [number, number] = [1, 2];

// 배열 메서드를 이용해 고정된 길이를 무시하고 요소를 추가/삭제 할 수 있다.
tup3.push(1);
tup3.push(1);
tup3.push(1);
tup3.push(1);


// 튜플의 장점
const users = [
    ["이정환", 1],
    ["이아무개", 2],
    ["김아무개", 3],
    ["박아무개", 4],
    [5, "조아무개"], // <- 새로 추가함 // 에러가 발생하지 않음
];

const users2: [string, number][] = [
    ["이정환", 1],
    ["이아무개", 2],
    ["김아무개", 3],
    ["박아무개", 4],
    [5, "조아무개"], // 오류 발생
];
