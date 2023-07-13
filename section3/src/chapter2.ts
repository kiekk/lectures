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

/**
 * Never 타입
 */

function neverExam() {
    function neverFunc(): never {
        while (true) {
        }
    }

    // never 타입은 모든 타입의 서브 타입이므로 업 캐스팅이 가능하므로 할당 가능
    let num: number = neverFunc()
    let str: string = neverFunc()
    let bool: boolean = neverFunc()

    // 다운 캐스팅은 불가능
    // let never1: never = 10 // 오류
    // let never2: never = 'string' // 오류
    // let never3: never = true // 오류
}
