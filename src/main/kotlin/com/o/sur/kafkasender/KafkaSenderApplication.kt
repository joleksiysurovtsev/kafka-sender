package com.o.sur.kafkasender

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaSenderApplication

fun main(args: Array<String>) {
	runApplication<KafkaSenderApplication>(*args)
}