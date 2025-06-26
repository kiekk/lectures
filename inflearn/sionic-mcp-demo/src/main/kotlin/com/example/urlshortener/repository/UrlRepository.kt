package com.example.urlshortener.repository

interface UrlRepository {
    fun save(shortKey: String, originalUrl: String)
    fun findByShortKey(shortKey: String): String?
    fun existsByShortKey(shortKey: String): Boolean
    fun getAllMappings(): Map<String, String>
    fun clear()
}
