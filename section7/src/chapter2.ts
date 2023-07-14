/**
 * map 메서드
 */

const arr = [1, 2, 3];
const newArr = arr.map((it) => it * 2);
// [2, 4, 6]


// map 메서드 직접 정의
function map<T, U>(arr: T[], callback: (item: T) => U): U[] {
    let result = [];
    for (let i = 0; i < arr.length; i++) {
        result.push(callback(arr[i]));
    }
    return result;
}

const numberMap = map(arr, (it) => it * 2)
const stringMap = map(['hi', 'hello'], (it) => it.toUpperCase())
const stringMap2 = map(['hi', 'hello'], (it) => parseInt(it))

/**
 * forEach
 */

const arr2 = [1, 2, 3];

arr2.forEach((it) => console.log(it));

// 출력 : 1, 2, 3

function forEach<T>(arr: T[], callback: (item: T) => void) {
    for (let i = 0; i < arr.length; i++) {
        callback(arr[i]);
    }
}