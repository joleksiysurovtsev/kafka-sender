package com.o.sur.kafkasender

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api/kafka")
class KafkaController(private val kafkaProducer: KafkaProducer) {

    @PostMapping("/sendBulk")
    fun sendBulkMessages(@RequestBody payloads: List<Map<String, Any>>) {
        kafkaProducer.sendMessages(payloads)
    }
}