package io.tstojkovski.prometheus.playground.api

import io.tstojkovski.prometheus.playground.service.PersonsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class Person(val name: String, val age: Int)

@RestController
@RequestMapping("/test")
class TestController(val service: PersonsService) {
    @GetMapping
    fun persons(name: String): List<Person> {
        return service.getPersons(name, arrayOf("status", "OK"), doubleArrayOf(0.5, 0.9, 0.95))
    }
}