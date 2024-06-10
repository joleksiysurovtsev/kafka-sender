package com.o.sur.kafkasender

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*


@Service
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, Any>) {

    fun sendMessages(payloads: List<Any>) = runBlocking {
        val headers = mapOf(
            "actionType" to "LABEL_INSTANCE_CREATE",
            "messageOriginator" to "sender",
            "actionId" to UUID.randomUUID().toString(),
            "parentActionId" to UUID.randomUUID().toString()
        )

        val recordHeaders = headers.map { (key, value) -> RecordHeader(key, value.toByteArray()) }

        payloads.map { payload ->
            async(Dispatchers.IO) {
                val record = ProducerRecord<String, Any>("EVENT_TOPIC", null, null, null, payload, recordHeaders)
                kafkaTemplate.send(record).get() // `.get()` используется для ожидания завершения отправки
            }
        }.forEach { it.await() }
    }
}