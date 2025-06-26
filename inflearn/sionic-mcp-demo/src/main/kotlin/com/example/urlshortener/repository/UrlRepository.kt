package com.example.urlshortener.repository

import com.example.urlshortener.dto.UrlMappingInfo
import java.time.Instant

interface UrlRepository {
    fun save(shortKey: String, originalUrl: String, createdAt: Instant = Instant.now())
    fun findByShortKey(shortKey: String): String?
    fun existsByShortKey(shortKey: String): Boolean
    fun getAllMappings(): Map<String, String>
    fun getAllMappingsWithInfo(): List<UrlMappingInfo>
    fun deleteByShortKey(shortKey: String): Boolean
    fun clear()
}
