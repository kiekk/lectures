package com.example.urlshortener.controller

import com.example.urlshortener.dto.ShortenRequest
import com.example.urlshortener.dto.ShortenResponse
import com.example.urlshortener.service.UrlShortenerService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import jakarta.validation.Valid

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api")
class UrlShortenerController(
    private val urlShortenerService: UrlShortenerService
) {

    @PostMapping("/shorten")
    fun shortenUrl(@Valid @RequestBody request: ShortenRequest): ResponseEntity<ShortenResponse> {
        logger.info { "URL 단축 요청 받음: ${request.originalUrl}" }
        
        val shortenResponse = urlShortenerService.shortenUrl(request.originalUrl)
        
        logger.info { "URL 단축 완료: ${request.originalUrl} -> ${shortenResponse.shortUrl}" }
        return ResponseEntity.ok(shortenResponse)
    }

    @GetMapping("/shorten/{shortKey}")
    fun getOriginalUrl(@PathVariable shortKey: String): ResponseEntity<String> {
        logger.info { "단축 URL 조회 요청: $shortKey" }
        
        val originalUrl = urlShortenerService.getOriginalUrl(shortKey)
        
        return if (originalUrl != null) {
            logger.info { "단축 URL 조회 성공: $shortKey -> $originalUrl" }
            ResponseEntity.ok(originalUrl)
        } else {
            logger.info { "단축 URL 조회 실패: $shortKey (존재하지 않음)" }
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/redirect/{shortKey}")
    fun redirectToOriginalUrl(@PathVariable shortKey: String): RedirectView {
        logger.info { "리다이렉트 요청: $shortKey" }
        
        val originalUrl = urlShortenerService.getOriginalUrl(shortKey)
        
        return if (originalUrl != null) {
            logger.info { "리다이렉트 실행: $shortKey -> $originalUrl" }
            RedirectView(originalUrl)
        } else {
            logger.info { "리다이렉트 실패: $shortKey (존재하지 않음)" }
            RedirectView("/error")
        }
    }
}
