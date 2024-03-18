package com.example.spring.kafka.demo.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Appliation event that is published, when a person entity is modified. The
 * interface to Kafka is decoupled and consumes the events.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PersonEvent extends PersonDto {
    public enum Event {
        CREATED,
        UPDATED,
        DELETED;
    }

    private Event event;
}
