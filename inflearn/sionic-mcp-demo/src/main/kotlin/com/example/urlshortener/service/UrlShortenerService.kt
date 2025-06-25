package com.example.urlshortener.service

import com.example.urlshortener.dto.ShortenResponse
import com.example.urlshortener.repository.UrlRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

@Service
class UrlShortenerService(
    private val urlRepository: UrlRepository,
    @Value("\${app.base-url:http://localhost:8080}") private val baseUrl: String
) {

    fun shortenUrl(originalUrl: String): ShortenResponse {
        logger.info { "URL 단축 서비스 시작: $originalUrl" }
        
        // 랜덤 6자리 키 생성 (문자로 시작, 문자와 숫자 조합)
        val shortKey = generateShortKey()
        logger.info { "생성된 단축 키: $shortKey" }
        
        // 저장소에 저장
        urlRepository.save(shortKey, originalUrl)
        logger.info { "URL 매핑 저장 완료: $shortKey -> $originalUrl" }
        
        val shortUrl = "$baseUrl/api/redirect/$shortKey"
        return ShortenResponse(
            shortKey = shortKey,
            shortUrl = shortUrl,
            originalUrl = originalUrl
        )
    }

    fun getOriginalUrl(shortKey: String): String? {
        logger.info { "원본 URL 조회 시작: $shortKey" }
        
        val originalUrl = urlRepository.findByShortKey(shortKey)
        
        if (originalUrl != null) {
            logger.info { "원본 URL 조회 성공: $shortKey -> $originalUrl" }
        } else {
            logger.info { "원본 URL 조회 실패: $shortKey (키가 존재하지 않음)" }
        }
        
        return originalUrl
    }

    private fun generateShortKey(): String {
        val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val alphanumeric = letters + "0123456789"
        
        // 첫 번째 문자는 반드시 문자
        val firstChar = letters[Random.nextInt(letters.length)]
        
        // 나머지 5자리는 문자와 숫자 조합
        val remainingChars = (1..5).map { 
            alphanumeric[Random.nextInt(alphanumeric.length)] 
        }.joinToString("")
        
        val shortKey = firstChar + remainingChars
        
        // 중복 키 체크 및 재생성
        return if (urlRepository.existsByShortKey(shortKey)) {
            logger.info { "중복 키 발견, 재생성: $shortKey" }
            generateShortKey() // 재귀적으로 다시 생성
        } else {
            shortKey
        }
    }
}
