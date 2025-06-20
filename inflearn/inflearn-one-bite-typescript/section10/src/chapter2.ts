/**
 * Pick<T, K>
 * → 뽑다, 고르다
 * → 객체 타입으로부터 특정 프로퍼티만 뽑아내는 타입
 */

interface Post {
    title: string
    tags: string[]
    content: string
    thumbnailURL?: string
}

const legacyPost: Pick<Post, 'title' | 'content'> = {
    title: '옛날 글',
    content: '옛날 컨텐츠'
}

// Pick<T, K> 직접 구현
type Pick<T, K extends keyof T> = {
    [key in K]: T[key]
}

/**
 * Omit<T, K>
 * → 생략하다, 빼다
 * → 객체 타입으로부터 특정 프로퍼티를 제거하는 타입
 */

const noTitlePost: Omit<Post, 'title'> = {
    content: '',
    tags: [],
    thumbnailURL: ''
}

// Omit<T, K> 직접 구현
type Omit<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>>

/**
 * Record<T, K>
 * →
 */

type ThumbnailLegacy = {
    large: {
        url: string
    }
    medium: {
        url: string
    }
    smail: {
        url: string
    }
}

type Thumbnail = Record<'large' | 'medium' | 'small', { url: string }>

// Record<K, V> 직접 구현
type Record<K extends keyof any, V> = {
    [key in K]: V
}