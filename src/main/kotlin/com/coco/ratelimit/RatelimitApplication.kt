package com.coco.ratelimit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RatelimitApplication

fun main(args: Array<String>) {
	runApplication<RatelimitApplication>(*args)
}
