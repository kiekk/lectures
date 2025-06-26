package com.example.urlshortener.repository

import com.example.urlshortener.dto.UrlMappingInfo
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

@Repository
@ConditionalOnProperty(value = ["app.storage-type"], havingValue = "in-memory", matchIfMissing = true)
class InMemoryUrlRepository : UrlRepository {
    
    private val urlMappings = ConcurrentHashMap<String, String>()
    private val createdAtMappings = ConcurrentHashMap<String, Instant>()

    init {
        logger.info { "In-Memory URL Repository 초기화 완료" }
    }

    override fun save(shortKey: String, originalUrl: String, createdAt: Instant) {
        urlMappings[shortKey] = originalUrl
        createdAtMappings[shortKey] = createdAt
        logger.info { "In-Memory에 URL 매핑 저장: $shortKey -> $originalUrl" }
        logger.info { "현재 저장된 URL 개수: ${urlMappings.size}" }
    }

    override fun findByShortKey(shortKey: String): String? {
        val result = urlMappings[shortKey]
        logger.info { "In-Memory에서 URL 매핑 조회: $shortKey -> ${result ?: "없음"}" }
        return result
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

    override fun getAllMappingsWithInfo(): List<UrlMappingInfo> {
        val result = urlMappings.map { (shortKey, originalUrl) ->
            UrlMappingInfo(
                shortKey = shortKey,
                originalUrl = originalUrl,
                createdAt = createdAtMappings[shortKey] ?: Instant.now()
            )
        }.sortedByDescending { it.createdAt }
        
        logger.info { "In-Memory에서 전체 URL 매핑 정보 조회 (총 ${result.size}개)" }
        return result
    }

    override fun deleteByShortKey(shortKey: String): Boolean {
        val originalUrl = urlMappings.remove(shortKey)
        createdAtMappings.remove(shortKey)
        val deleted = originalUrl != null
        
        if (deleted) {
            logger.info { "In-Memory에서 URL 매핑 삭제: $shortKey -> $originalUrl" }
            logger.info { "현재 저장된 URL 개수: ${urlMappings.size}" }
        } else {
            logger.info { "In-Memory에서 URL 매핑 삭제 실패: $shortKey (존재하지 않음)" }
        }
        
        return deleted
    }

    override fun clear() {
        val count = urlMappings.size
        urlMappings.clear()
        createdAtMappings.clear()
        logger.info { "In-Memory에서 모든 URL 매핑 삭제 (삭제된 개수: $count)" }
    }
}
