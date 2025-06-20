interface Board {
    title: string
}

interface Board {
    type: string
}

// 같은 interface 명으로 Board 를 다시 만든다면, 자동으로 확장이 된다.

const board: Board = {
    title: 'board title',
    type: 'board type'
}

// type Board2 = {
//     title: string
// } // ❌

// type Board2 = {
//     type: string
// } // ❌
// TS2300: Duplicate identifier 'Board2'.

// 타입 별칭은 computed value 사용 가능
// 인터페이스는 불가능

type names = 'firstName' | 'lastName'

type NameTypes = {
    [key in names]: string
}

const yc: NameTypes = { firstName: 'hi', lastName: 'yc' }

interface NameInterface {
    // error
    // [key in names]: string
}