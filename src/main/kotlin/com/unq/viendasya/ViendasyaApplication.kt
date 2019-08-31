package com.unq.viendasya

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ViendasyaApplication

fun main(args: Array<String>) {
	runApplication<ViendasyaApplication>(*args)
}
