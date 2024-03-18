package com.example.spring.kafka.demo.service;

import org.mapstruct.Mapper;

import com.example.spring.kafka.avro.person.Person;

/**
 * Provides all required mappings for person entities.
 */
@Mapper
interface PersonKafkaMapper {
    Person toKafkaEvent(PersonEvent event);

    PersonEvent toPersonEvent(Person event);

    default String map(CharSequence value) {
        return value == null ? null : value.toString();
    }
}
