/**
 * 타입스크립트의 클래스
 */

const employee = {
    name: 'soono',
    age: 10,
    position: 'developer',
    work() {
        console.log('일함')
    }
}

class Employee {
    name: string
    age: number
    position: string

    constructor(name: string, age: number, position: string) {
        this.name = name
        this.age = age
        this.position = position
    }

    // 메서드
    work() {
        console.log('일함')
    }
}

const employeeB: Employee = new Employee('soono', 10, '개발자');
console.log(employeeB)

// 타입으로도 사용 가능
const employeeC: Employee = {
    name: 'soono',
    age: 10,
    position: 'developer',
    work() {
    }
}

// 상속
class ExecutiveOfficer extends Employee {
    officeNumber: number

    constructor(name: string, age: number, position: string, officeNumber: number) {
        super(name, age, position);
        this.officeNumber = officeNumber

    }

}