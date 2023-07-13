/**
 * Unknown 타입
 */

function unknownExam() {
    // 업 캐스팅은 가능
    let a: unknown = 1 // number -> unknown
    let b: unknown = "hello" // string -> unknown
    let c: unknown = true // boolean -> unknown
    let d: unknown = null // null -> unknown
    let e: unknown = undefined // undefined -> unknown
    let f: unknown = [] // Array -> unknown
    let g: unknown = {} // Object -> unknown
    let h: unknown = () => {
    } // Function -> unknown

    // 다운 캐스팅은 불가능
    let unknownVar: unknown
    // let num: number = unknownVar // 오류
    // let str: string = unknownVar // 오류
    // let bool: boolean = unknownVar // 오류
}