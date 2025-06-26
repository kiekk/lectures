package com.example.urlshortener.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

@Configuration
@ConditionalOnProperty(value = ["app.storage-type"], havingValue = "postgres")
class DatabaseConfig {

    @Bean
    @Order(1)
    fun dataSource(): DataSource {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/urlshortener"
            username = "urlshortener_user"
            password = "urlshortener_password"
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
            minimumIdle = 5
            connectionTimeout = 20000
            idleTimeout = 600000
            maxLifetime = 1800000
        }
        
        val dataSource = HikariDataSource(hikariConfig)
        logger.info { "HikariCP DataSource 생성 완료" }
        return dataSource
    }

    @Bean
    @Order(2)
    fun database(dataSource: DataSource): Database {
        val database = Database.connect(dataSource)
        logger.info { "Exposed Database 연결 완료" }
        return database
    }
}
