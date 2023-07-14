/**
 * 맵드 타입
 * mapped type
 */

interface User {
    id: number
    name: string
    age: number
}

// 유저 조회
function fetchUser(): User {
    return {
        id: 1,
        name: 'soono',
        age: 10,
    }
}

// 유저 수정
function updateUser(user: User) {
    // 수정하는 기능
}

// updateUser({age: 15}) // ❌, { age: 15 }는 User 타입이 아니므로 오류 발생

type PartialUser = {
    id?: string
    name?: string
    age?: number
}

function updateUser2(user: PartialUser) {
    // 수정하는 기능
}

updateUser2({age: 15})


// 맵드 타입
type PartialUser2 = {
    [key in keyof User]?: User[key];
}

function updateUser3(user: PartialUser2) {
    // 수정하는 기능
}

updateUser3({age: 15})