package ru.ifmo.ted

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@SpringBootApplication
@EnableJdbcRepositories("ru.ifmo.ted.repository")
@EnableTransactionManagement
class TedApplication : SpringBootServletInitializer() {
    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder? {
        return builder.sources(TedApplication::class.java)
    }
}

fun main(args: Array<String>) {
    runApplication<TedApplication>(*args)
}
