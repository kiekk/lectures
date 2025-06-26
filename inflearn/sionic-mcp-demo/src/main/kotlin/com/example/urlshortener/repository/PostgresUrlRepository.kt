package com.example.urlshortener.repository

import com.example.urlshortener.dto.UrlMappingInfo
import com.example.urlshortener.entity.UrlMappings
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import jakarta.annotation.PostConstruct
import java.time.Instant

private val logger = KotlinLogging.logger {}

@Repository
@ConditionalOnProperty(value = ["app.storage-type"], havingValue = "postgres")
class PostgresUrlRepository(
    private val database: Database
) : UrlRepository {

    @PostConstruct
    fun initializeDatabase() {
        try {
            transaction {
                SchemaUtils.create(UrlMappings)
            }
            logger.info { "PostgreSQL URL Repository 초기화 완료 - 테이블 생성됨" }
        } catch (e: Exception) {
            logger.error(e) { "PostgreSQL URL Repository 초기화 실패" }
            throw e
        }
    }

    override fun save(shortKey: String, originalUrl: String, createdAt: Instant) {
        transaction {
            try {
                UrlMappings.insert {
                    it[UrlMappings.shortKey] = shortKey
                    it[UrlMappings.originalUrl] = originalUrl
                    it[UrlMappings.createdAt] = createdAt
                }
                logger.info { "PostgreSQL에 URL 매핑 저장: $shortKey -> $originalUrl" }
            } catch (e: Exception) {
                logger.error(e) { "PostgreSQL URL 매핑 저장 실패: $shortKey -> $originalUrl" }
                throw e
            }
        }
    }

    override fun findByShortKey(shortKey: String): String? {
        return transaction {
            val result = UrlMappings
                .select { UrlMappings.shortKey eq shortKey }
                .singleOrNull()
                ?.get(UrlMappings.originalUrl)
            
            logger.info { "PostgreSQL에서 URL 매핑 조회: $shortKey -> ${result ?: "없음"}" }
            result
        }
    }

    override fun existsByShortKey(shortKey: String): Boolean {
        return transaction {
            val exists = UrlMappings
                .select { UrlMappings.shortKey eq shortKey }
                .count() > 0
            
            logger.info { "PostgreSQL에서 URL 매핑 존재 여부 확인: $shortKey -> $exists" }
            exists
        }
    }

    override fun getAllMappings(): Map<String, String> {
        return transaction {
            val mappings = UrlMappings
                .selectAll()
                .associate { row ->
                    row[UrlMappings.shortKey] to row[UrlMappings.originalUrl] 
                }
            
            logger.info { "PostgreSQL에서 전체 URL 매핑 조회 (총 ${mappings.size}개)" }
            mappings
        }
    }

    override fun getAllMappingsWithInfo(): List<UrlMappingInfo> {
        return transaction {
            val result = UrlMappings
                .selectAll()
                .orderBy(UrlMappings.createdAt to SortOrder.DESC)
                .map { row ->
                    UrlMappingInfo(
                        shortKey = row[UrlMappings.shortKey],
                        originalUrl = row[UrlMappings.originalUrl],
                        createdAt = row[UrlMappings.createdAt]
                    )
                }
            
            logger.info { "PostgreSQL에서 전체 URL 매핑 정보 조회 (총 ${result.size}개)" }
            result
        }
    }

    override fun deleteByShortKey(shortKey: String): Boolean {
        return transaction {
            val deletedCount = UrlMappings.deleteWhere { UrlMappings.shortKey eq shortKey }
            val deleted = deletedCount > 0
            
            if (deleted) {
                logger.info { "PostgreSQL에서 URL 매핑 삭제: $shortKey" }
            } else {
                logger.info { "PostgreSQL에서 URL 매핑 삭제 실패: $shortKey (존재하지 않음)" }
            }
            
            deleted
        }
    }

    override fun clear() {
        transaction {
            val deletedCount = UrlMappings.deleteAll()
            logger.info { "PostgreSQL에서 모든 URL 매핑 삭제 (삭제된 개수: $deletedCount)" }
        }
    }
}
