// enum 타입
var Role;
(function (Role) {
    Role[Role["ADMIN"] = 0] = "ADMIN";
    Role[Role["USER"] = 1] = "USER";
    Role[Role["GUEST"] = 2] = "GUEST";
})(Role || (Role = {}));
// console.log(Role.ADMIN)
// console.log(Role.USER)
// console.log(Role.GUEST)
var Role2;
(function (Role2) {
    Role2[Role2["ADMIN"] = 10] = "ADMIN";
    Role2[Role2["USER"] = 11] = "USER";
    Role2[Role2["GUEST"] = 12] = "GUEST";
})(Role2 || (Role2 = {}));
console.log(Role2.ADMIN);
console.log(Role2.USER);
console.log(Role2.GUEST);
// 문자열 enum
var Role3;
(function (Role3) {
    Role3["ADMIN"] = "admin";
    Role3["USER"] = "user";
    Role3["GUEST"] = "guest";
})(Role3 || (Role3 = {}));
console.log(Role3.ADMIN);
console.log(Role3.USER);
console.log(Role3.GUEST);
// enum 값 출력
console.log(Role2[10]); // ADMIN
console.log(Role2[11]); // USER
console.log(Role2[12]); // GUEST
// 문자열 enum은 에러
console.log(Role3['ADMIN']);
