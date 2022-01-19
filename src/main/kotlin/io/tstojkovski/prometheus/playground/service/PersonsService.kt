package io.tstojkovski.prometheus.playground.service

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import io.tstojkovski.prometheus.playground.api.Person
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import kotlin.random.nextUInt

@Service
class PersonsService(val meterRegistry: MeterRegistry) {
    private val cache = ConcurrentHashMap<List<String>, Timer>()

    fun getPersons(
        name: String,
        staticTagsWithValues: Array<String>,
        percentiles: DoubleArray,
    ): List<Person> {
        val random = Random(name.hashCode())
        val persons = listOf(Person("John", 30), Person("Jane", 25))

        record(name, staticTagsWithValues, percentiles, Duration.ofMillis(random.nextUInt(100u).toLong()), "getPersons", "yea yea")

        return persons
    }

    fun record(
        name: String,
        staticTagsWithValues: Array<String>,
        percentiles: DoubleArray,
        duration: Duration,
        vararg dynamicTagKV: String
    ) {
        val timer = cache.computeIfAbsent(dynamicTagKV.asList() + listOf(name)) {
            Timer.builder(name)
                .tags(*staticTagsWithValues)
                .tags(*dynamicTagKV)
                .publishPercentiles(*percentiles)
                .register(meterRegistry)
        }
        timer.record(duration)
    }
}