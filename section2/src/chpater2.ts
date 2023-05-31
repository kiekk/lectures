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