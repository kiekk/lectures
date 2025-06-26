package com.example.urlshortener.service

import com.example.urlshortener.dto.ShortenResponse
import com.example.urlshortener.dto.UrlMappingInfo
import com.example.urlshortener.repository.UrlRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

@Service
class UrlShortenerService(
    private val urlRepository: UrlRepository,
    @Value("\${app.base-url:http://localhost:8080}") private val baseUrl: String
) {

    companion object {
        private const val CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        private const val SHORT_KEY_LENGTH = 6
    }

    fun shortenUrl(originalUrl: String): ShortenResponse {
        logger.info { "URL 단축 서비스 시작: $originalUrl" }

        val shortKey = generateUniqueShortKey()
        val createdAt = Instant.now()
        
        urlRepository.save(shortKey, originalUrl, createdAt)
        
        val shortUrl = "$baseUrl/api/redirect/$shortKey"
        
        logger.info { "URL 매핑 저장 완료: $shortKey -> $originalUrl" }
        
        return ShortenResponse(
            shortKey = shortKey,
            shortUrl = shortUrl,
            originalUrl = originalUrl,
            createdAt = createdAt
        )
    }

    fun getOriginalUrl(shortKey: String): String? {
        logger.info { "원본 URL 조회 시작: $shortKey" }
        
        val originalUrl = urlRepository.findByShortKey(shortKey)
        
        if (originalUrl != null) {
            logger.info { "원본 URL 조회 성공: $shortKey -> $originalUrl" }
        } else {
            logger.info { "원본 URL 조회 실패: $shortKey (존재하지 않음)" }
        }
        
        return originalUrl
    }

    fun getAllUrls(): List<UrlMappingInfo> {
        logger.info { "모든 URL 목록 조회 시작" }
        
        val urls = urlRepository.getAllMappingsWithInfo()
        
        logger.info { "모든 URL 목록 조회 완료: ${urls.size}개" }
        return urls
    }

    fun deleteUrl(shortKey: String): Boolean {
        logger.info { "URL 삭제 시작: $shortKey" }
        
        val deleted = urlRepository.deleteByShortKey(shortKey)
        
        if (deleted) {
            logger.info { "URL 삭제 성공: $shortKey" }
        } else {
            logger.info { "URL 삭제 실패: $shortKey (존재하지 않음)" }
        }
        
        return deleted
    }

    private fun generateUniqueShortKey(): String {
        var shortKey: String
        do {
            shortKey = generateRandomString()
        } while (urlRepository.existsByShortKey(shortKey))
        
        logger.info { "생성된 단축 키: $shortKey" }
        return shortKey
    }

    private fun generateRandomString(): String {
        return (1..SHORT_KEY_LENGTH)
            .map { CHARSET[Random.nextInt(CHARSET.length)] }
            .joinToString("")
    }
}
