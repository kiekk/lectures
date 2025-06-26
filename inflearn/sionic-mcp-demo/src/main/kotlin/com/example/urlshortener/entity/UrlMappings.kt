package com.example.urlshortener.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object UrlMappings : IntIdTable("url_mappings") {
    val shortKey = varchar("short_key", 10).uniqueIndex()
    val originalUrl = text("original_url")
    val createdAt = timestamp("created_at").default(Instant.now())
    val updatedAt = timestamp("updated_at").default(Instant.now())
}
