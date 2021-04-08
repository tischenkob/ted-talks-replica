package ru.ifmo.ted

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TedApplication

fun main(args: Array<String>) {
	runApplication<TedApplication>(*args)
}
