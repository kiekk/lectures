package com.example.urlshortener.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class UrlMappingInfo(
    val shortKey: String,
    val originalUrl: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    val createdAt: Instant
)
