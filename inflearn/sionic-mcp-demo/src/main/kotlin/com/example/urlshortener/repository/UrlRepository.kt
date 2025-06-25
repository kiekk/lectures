package com.example.urlshortener.repository

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

@Repository
class UrlRepository {
    
    private val urlMappings: MutableMap<String, String> = ConcurrentHashMap()
    
    fun save(shortKey: String, originalUrl: String) {
        logger.info { "URL 매핑 저장: $shortKey -> $originalUrl" }
        urlMappings[shortKey] = originalUrl
        logger.info { "현재 저장된 URL 개수: ${urlMappings.size}" }
    }
    
    fun findByShortKey(shortKey: String): String? {
        val originalUrl = urlMappings[shortKey]
        logger.info { "URL 매핑 조회: $shortKey -> ${originalUrl ?: "없음"}" }
        return originalUrl
    }
    
    fun existsByShortKey(shortKey: String): Boolean {
        val exists = urlMappings.containsKey(shortKey)
        logger.info { "URL 매핑 존재 여부 확인: $shortKey -> $exists" }
        return exists
    }
    
    fun getAllMappings(): Map<String, String> {
        logger.info { "전체 URL 매핑 조회 (총 ${urlMappings.size}개)" }
        return urlMappings.toMap()
    }
    
    fun clear() {
        logger.info { "모든 URL 매핑 삭제" }
        urlMappings.clear()
    }
}
