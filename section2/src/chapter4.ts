// 타입 별칭 (Type Alias)
type User = {
    id: number;
    name: string;
    nickname: string;
    birth: string;
    bio: string;
    location: string;
};

let user: User = {
    id: 1,
    name: "이정환",
    nickname: "winterlood",
    birth: "1997.01.07",
    bio: "안녕하세요",
    location: "부천시",
};

let user2: User = {
    id: 2,
    name: "홍길동",
    nickname: "winterlood",
    birth: "1997.01.07",
    bio: "안녕하세요",
    location: "부천시",
};

// TS2300: Duplicate identifier 'User'.
// type User = {}


// 스코프가 다르면 상관 없음
function test() {
    type User = string;
}


// index signature
type CountryCodes = {
    Korea: string;
    UnitedState: string;
    UnitedKingdom: string;
};

let countryCodes: CountryCodes = {
    Korea: "ko",
    UnitedState: "us",
    UnitedKingdom: "uk",
};

// korea 프로퍼티는 반드시 포함되어야 함.
type CountryNumberCodes = {
    [key: string]: number;
    Korea: number;
};

// 명시한 프로퍼티의 value 타입은 인덱스 시그니처의 value 타입과 호환되거나 일치해야 함
// type CountryNumberCodes2 = {
//     [key: string]: number;
//     Korea: string; // 오류!
// };
// TS2411: Property 'Korea' of type 'string' is not assignable to 'string' index type 'number'.