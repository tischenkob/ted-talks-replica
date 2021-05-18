package ru.ifmo.ted.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.relational.core.mapping.NamingStrategy
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionManager
import org.springframework.transaction.jta.JtaTransactionManager
import javax.sql.DataSource


@Configuration
class JdbcConfiguration(val environment: Environment) : AbstractJdbcConfiguration() {

    @Bean
    fun transactionManager(): PlatformTransactionManager = JtaTransactionManager()

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource?): NamedParameterJdbcOperations? =
        NamedParameterJdbcTemplate(dataSource!!)

    @Bean
    fun transactionManager(dataSource: DataSource?): TransactionManager? = DataSourceTransactionManager(dataSource!!)

    @Bean
    fun namingStrategy(): NamingStrategy? {
        return object : NamingStrategy {
            override fun getSchema(): String {
                return environment.getProperty("ted.database.schema")!!
            }
        }
    }
}
