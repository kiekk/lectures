/**
 * 접근 제어자
 * access modifier
 * → public private protected
 */

class Employee {

    constructor(private name: string, private age: number, private position: string) {
    }

    work() {
        console.log('일함')
    }
}

const employee: Employee = new Employee('soono', 10, '개발자');
// employee.name = '홍길동' // 오류
// employee.age = 10 // 오류
// employee.position = '디자이너' // 오류

class ExecutiveOfficer extends Employee {
    officeNumber: number

    constructor(name: string, age: number, position: string, officeNumber: number) {
        super(name, age, position);
        this.officeNumber = officeNumber

    }

    func() {
        // this.name // ❌, private 은 해당 클래스 내에서만 접근 가능
    }

}