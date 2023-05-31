// number
let num1: number = 123;
let num2: number = -123;
let num3: number = 0.123;
let num4: number = .123;
let num5: number = -0.123;
let num6: number = -.123;
let num7: number = Infinity;
let num8: number = -Infinity;
let num9: number = -NaN;

// string
let str1: string = "hello";
let str2: string = 'hello';
let str3: string = `hello`;
let str4: string = `hello ${str1}`; // template literal string

// boolean
let bool1 : boolean = true;
let bool2 : boolean = false;

// null
let null1: null = null;

// null 타입은 null 외에 다른 값을 할당할 수 없음.
// null1 = undefined
// null1 = 1;
// null1 = 'hello';

// undefined 타입
let unde1: undefined = undefined;

// undefined 타입 역시 undefined 외에 다른 값을 할당할 수 없음.
// unde1 = null1
// unde1 = 1
// unde1 = 'hello'


// "strictNullChecks": false로 다른 타입에 null 할당하기
let num10: number = null