package com.example.urlshortener.repository

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

@Repository
@ConditionalOnProperty(value = ["app.storage-type"], havingValue = "in-memory", matchIfMissing = true)
class InMemoryUrlRepository : UrlRepository {
    
    private val urlMappings: MutableMap<String, String> = ConcurrentHashMap()
    
    init {
        logger.info { "In-Memory URL Repository 초기화 완료" }
    }
    
    override fun save(shortKey: String, originalUrl: String) {
        logger.info { "In-Memory에 URL 매핑 저장: $shortKey -> $originalUrl" }
        urlMappings[shortKey] = originalUrl
        logger.info { "현재 저장된 URL 개수: ${urlMappings.size}" }
    }
    
    override fun findByShortKey(shortKey: String): String? {
        val originalUrl = urlMappings[shortKey]
        logger.info { "In-Memory에서 URL 매핑 조회: $shortKey -> ${originalUrl ?: "없음"}" }
        return originalUrl
    }
    
    override fun existsByShortKey(shortKey: String): Boolean {
        val exists = urlMappings.containsKey(shortKey)
        logger.info { "In-Memory에서 URL 매핑 존재 여부 확인: $shortKey -> $exists" }
        return exists
    }
    
    override fun getAllMappings(): Map<String, String> {
        logger.info { "In-Memory에서 전체 URL 매핑 조회 (총 ${urlMappings.size}개)" }
        return urlMappings.toMap()
    }
    
    override fun clear() {
        logger.info { "In-Memory에서 모든 URL 매핑 삭제" }
        urlMappings.clear()
    }
}
