package com.example.urlshortener.dto

data class ShortenResponse(
    val shortKey: String,
    val shortUrl: String,
    val originalUrl: String
)
