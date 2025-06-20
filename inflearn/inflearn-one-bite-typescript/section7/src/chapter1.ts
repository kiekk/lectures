/**
 * 첫번째 사례
 */

function swap<T>(a: T, b: T) {
    return [b, a]
}

const [a, b] = swap(1, 2)

// const [c, d] = swap('1', 2) // ❌ 서로 다른 타입을 전달할 수 없다.

// 제네릭에 타입을 여러 개 지정
function swap2<T, U>(a: T, b: U) {
    return [b, a]
}

const [e, f] = swap2("1", 2)

/**
 * 두번째 사례
 */

function returnFirstValue(data: any) {
    return data[0]
}

// num, str 이 any 타입으로 반환됨
const num = returnFirstValue(([0, 1, 2])) // 0
const str = returnFirstValue(['hello', 'mynameis']) // hello

function returnFirstValue2<T>(data: T[]) {
    return data[0]
}

const num2 = returnFirstValue2(([0, 1, 2])) // 0
const str2 = returnFirstValue2(['hello', 'mynameis']) // hello

// 배열에 서로 다른 타입을 전달 할 경우 반환 타입은 전달된 타입의 union 타입이 됩니다.
// str3 는 실제 1을 반환하지만 타입은 string 과 number의 union 타입이 됩니다.
const str3 = returnFirstValue2([1, 'hello', 'mynameis']) // 1

function returnFirstValue3<T>(data: [T, ...unknown[]]) {
    return data[0]
}

const str4 = returnFirstValue3([1, 'hello', 'mynameis']) // 1

/**
 * 세번째 사례
 */

// 이 함수는 전달된 타입의 관계없이 length property만 있으면 정상
function getLength(data: any) {
    return data.length
}

const var1 = getLength([1, 2, 3]) // 3
const var2 = getLength('12345') // 5
const var3 = getLength({length: 10}) // 5
const var4 = getLength(10)

// 제네릭 타입을 제한, length 프로퍼티를 가지고 있는 타입만 T에 할당될 수 있음
function getLength2<T extends { length: number }>(data: T) {
    return data.length
}

const var5= getLength2([1, 2, 3]) // 3
const var6 = getLength2('12345') // 5
const var7 = getLength2({length: 10}) // 5
// const var8 = getLength2(10) // 10은 length 가 없기 때문에 X