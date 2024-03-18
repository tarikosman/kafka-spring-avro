package com.example.spring.kafka.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.spring.kafka.avro.person.Person;
import com.example.spring.kafka.demo.config.DemoConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Connector to Kafka. Be aware, that in most cases, it is better to use
 * Debezium. This allows to persist the entity and the kafka data in one
 * transaction. Hence, Debezium provides an outbox pattern, which decouples the
 * kafka transmission.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PersonKafkaConnector {
    private final KafkaTemplate<String, Person> kafkaTemplate;
    private final PersonKafkaMapper personMapper;

    @TransactionalEventListener
    void produce(PersonEvent event) {
        var kafkaEvent = personMapper.toKafkaEvent(event);
        kafkaTemplate.send(DemoConfig.TOPIC_NAME_DB_DEMO, event.getId().toString(), kafkaEvent);
    }

    @KafkaListener(id = "kafka-spring-avro-demo", topics = DemoConfig.TOPIC_NAME_DB_DEMO)
    public void listen(Person person) {
        if (log.isInfoEnabled()) {
            log.info("Received event{}", personMapper.toPersonEvent(person));
        }
    }
}
