package com.example.urlshortener.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class ShortenResponse(
    val shortKey: String,
    val shortUrl: String,
    val originalUrl: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    val createdAt: Instant = Instant.now()
)
