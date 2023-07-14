/**
 * 인덱스드 엑세스 타입
 */

// 객체
interface Post {
    title: string
    content: string
    author: {
        id: number
        name: string
    }
}

const post: Post = {
    title: '게시글 제목',
    content: '게시글 본문',
    author: {
        id: 1,
        name: 'soono'
    }
}

function printAuthorInfo(author: Post['author']) {
    console.log(`${author.id} - ${author.name}`);
}


// 배열
type PostList = {
    title: string
    content: string
    author: {
        id: number
        name: string
    }
}[]

const post2: PostList[number] = {
    title: '게시글 제목',
    content: '게시글 본문',
    author: {
        id: 1,
        name: 'soono'
    }
}

const post3: PostList[0] = {
    title: '게시글 제목',
    content: '게시글 본문',
    author: {
        id: 1,
        name: 'soono'
    }
}

function printAuthorInfoFromArray(author: PostList[number]['author']) {
    console.log(`${author.id} - ${author.name}`);
}


// 튜플
type Tup = [number, string, boolean]

type Tup0 = Tup[0] // number
type Tup1 = Tup[1] // string
type Tup2 = Tup[2] // boolean
type TupNum = Tup[number] // string | number | boolean