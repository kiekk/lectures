/**
 * 프로미스
 */

const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
        resolve(20)
    }, 3000)
})

promise
    .then((response) => {
        console.log(response) // 20
    })

const promise2 = new Promise<number>((resolve, reject) => {
    setTimeout(() => {
        resolve(20)
        // reject('test')
    }, 3000)
})

promise2
    .then((response) => {
        console.log(response * 10) // 200
    })
    .catch((err) => {
        // reject의 매개변수 타입은 any
    })

/**
 * 프로미스를 반환하는 함수의 타입을 정의
 */

interface Post {
    id: number
    title: string
    content: string
}

function fetchPost(): Promise<Post> {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            resolve({
                id: 1,
                title: '게시글 제목',
                content: '게시글 컨텐츠'
            })
        }, 3000)
    })
}

const postRequest = fetchPost()

postRequest
    .then((post) => {
        // resolve로 넘어오는 매개변수 post 는 Post 타입
    })