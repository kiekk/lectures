package com.example.urlshortener.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

object UrlMappings : Table() {
    val shortKey = varchar("short_key", 10)
    val originalUrl = text("original_url")
    val createdAt = timestamp("created_at").default(Instant.now())
    
    override val primaryKey = PrimaryKey(shortKey)
}
