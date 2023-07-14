/**
 * 클래스
 */

let studentA = {
    name: 'soono',
    grade: 'A+',
    age: 10,
    study() {
        console.log('열심히 공부함')
    },
    introduce() {
        console.log('안녕하세요')
    }
}

let studentB = {
    name: '홍길동',
    grade: 'B+',
    age: 10,
    study() {
        console.log('열심히 공부함')
    },
    introduce() {
        console.log('안녕하세요')
    }
}

class Student {
    // 필드
    name;
    age;
    grade;

    // 생성자
    constructor(name, grade, age) {
        this.name = name;
        this.grade = grade;
        this.age = age;
    }

    // 메서드
    study() {
        console.log("열심히 공부 함");
    }

    introduce() {
        console.log(`안녕하세요 ${this.name} 입니다!`);
    }
}

const studentC = new Student("홍길동", "A+", 27);

console.log(studentC);
// Student { name: '홍길동', age: 27, grade: 'A+' }

studentC.study(); // 열심히 공부 함
studentB.introduce(); // 안녕하세요 홍길동 입니다

// 상속
class StudentDeveloper extends Student {
    // 필드
    favoriteSkill;

    // 생성자
    constructor(name, grade, age, favoriteSkill) {
        super(name, grade, age);
        this.favoriteSkill = favoriteSkill;
    }

    // 메서드
    programming() {
        console.log(`${this.favoriteSkill}로 프로그래밍 함`);
    }
}