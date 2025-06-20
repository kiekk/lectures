/**
 * Partial<T>
 * → 부분적인, 일반적인
 * → 특정 객체 타입의 모든 프로퍼티를 선택적 프로퍼티로 바꿔주는 타입
 */

interface Post {
    title: string
    tags: string[]
    content: string
    thumbnailURL?: string
}

// 임시 저장 글
const draft: Partial<Post> = {
    title: '제목 나중에',
    content: '초안'
}

// Partial<T> 직접 구현
type Partial<T> = {
    [key in keyof T]?: T[key]
}

/**
 * Required<T>
 * → 필수의, 필수적인
 * → 특정 객체 타입의 모든 프로퍼티를 필수 프로퍼티로 바꿔주는 타입
 */

const withThumbnailPost: Post = {
    title: '한입 타스 후기',
    tags: ['ts'],
    content: '',
    // thumbnailURL: 'https;//' // 선택적 프로퍼티라 없어도 오류 발생 X
}

const withThumbnailPost2: Required<Post> = {
    title: '한입 타스 후기',
    tags: ['ts'],
    content: '',
    thumbnailURL: 'https;//' // Required<T>로 필수 프로퍼티로 바뀌어서 없을 경우 오류 발생
}

// Required<T> 직접 구현
type Required<T> = {
    [key in keyof T]-?: T[key]
    // -? → 선택적 프로퍼티를 필수 프로퍼티로 변경
}

/**
 * Reaonly<T>
 * → 읽기전용, 수정불가
 * → 특정 객체 타입에서 모든 프로퍼티를 읽기 전용 프로퍼티로 만들어주는 타입
 */

const readonlyPost: Post = {
    title: '보호된 게시글입니다.',
    tags: [],
    content: ''
}

readonlyPost.title = '변경 가능'

const readonlyPost2: Readonly<Post> = {
    title: '보호된 게시글입니다.',
    tags: [],
    content: ''
}

// readonlyPost2.title = '변경 가능' // readonly 프로퍼티는 변경 불가

// Readonly<T> 직접 구현
type Readonly<T> = {
    readonly [key in keyof T]: T[key]
}