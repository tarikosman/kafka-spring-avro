package com.example.spring.kafka.demo.service;

import org.mapstruct.Mapper;

import com.example.spring.kafka.avro.person.Person;
import com.example.spring.kafka.demo.config.MapperSupport;

/**
 * Provides all required mappings for person entities.
 */
@Mapper
interface PersonKafkaMapper extends MapperSupport {
    Person toKafkaEvent(PersonEvent event);

    PersonEvent toPersonEvent(Person event);
}
