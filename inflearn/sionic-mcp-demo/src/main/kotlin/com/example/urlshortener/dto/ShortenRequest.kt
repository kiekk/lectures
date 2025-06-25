package com.example.urlshortener.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ShortenRequest(
    @field:NotBlank(message = "URL은 필수입니다")
    @field:Pattern(
        regexp = "^https?://.*",
        message = "올바른 URL 형식이어야 합니다 (http:// 또는 https://로 시작)"
    )
    val originalUrl: String
)
