// enum 타입
enum Role {
    ADMIN,
    USER,
    GUEST,
}

// console.log(Role.ADMIN)
// console.log(Role.USER)
// console.log(Role.GUEST)

enum Role2 {
    ADMIN = 10, // 10 할당
    USER, // 11 할당(자동)
    GUEST, // 12 할당(자동)
}

console.log(Role2.ADMIN)
console.log(Role2.USER)
console.log(Role2.GUEST)

// 문자열 enum
enum Role3 {
    ADMIN = 'admin',
    USER = 'user',
    GUEST = 'guest',
}

console.log(Role3.ADMIN)
console.log(Role3.USER)
console.log(Role3.GUEST)


// enum 값 출력
console.log(Role2[10]) // ADMIN
console.log(Role2[11]) // USER
console.log(Role2[12]) // GUEST

// 문자열 enum은 에러
console.log(Role3['ADMIN'])
