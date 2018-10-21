package com.osinskik.playground.restapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@SpringBootApplication
class RestappApplication

fun main(args: Array<String>) {
  runApplication<RestappApplication>(*args)
}


@RestController
class AppController {
  @GetMapping("/{pathParam}")
  fun get(@PathVariable("pathParam") pathParam: String) = Response(pathParam, Instant.now().toEpochMilli())
}

data class Response (
    val data:String,
    val date: Long
)