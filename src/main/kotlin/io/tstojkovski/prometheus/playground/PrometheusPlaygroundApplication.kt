package io.tstojkovski.prometheus.playground

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PrometheusPlaygroundApplication

fun main(args: Array<String>) {
    runApplication<PrometheusPlaygroundApplication>(*args)
}
