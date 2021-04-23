package ru.ifmo.ted

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableJdbcRepositories("ru.ifmo.ted.repository")
class TedApplication

fun main(args: Array<String>) {
	runApplication<TedApplication>(*args)
}
