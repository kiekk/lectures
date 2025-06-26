package com.example.urlshortener.repository

import com.example.urlshortener.entity.UrlMappings
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import jakarta.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Repository
@ConditionalOnProperty(value = ["app.storage-type"], havingValue = "postgres")
class PostgresUrlRepository(
    private val database: Database  // Database Bean을 명시적으로 주입
) : UrlRepository {

    @PostConstruct
    fun initializeDatabase() {
        try {
            // Database가 이미 주입되었으므로 바로 transaction 사용 가능
            transaction {
                SchemaUtils.create(UrlMappings)
            }
            logger.info { "PostgreSQL URL Repository 초기화 완료 - 테이블 생성됨" }
        } catch (e: Exception) {
            logger.error(e) { "PostgreSQL URL Repository 초기화 실패" }
            throw e
        }
    }

    override fun save(shortKey: String, originalUrl: String) {
        transaction {
            try {
                UrlMappings.insert {
                    it[UrlMappings.shortKey] = shortKey
                    it[UrlMappings.originalUrl] = originalUrl
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
                .associate { 
                    it[UrlMappings.shortKey] to it[UrlMappings.originalUrl] 
                }
            
            logger.info { "PostgreSQL에서 전체 URL 매핑 조회 (총 ${mappings.size}개)" }
            mappings
        }
    }

    override fun clear() {
        transaction {
            val deletedCount = UrlMappings.deleteAll()
            logger.info { "PostgreSQL에서 모든 URL 매핑 삭제 (삭제된 개수: $deletedCount)" }
        }
    }
}
