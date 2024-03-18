package com.example.spring.kafka.demo.config;

/**
 * Provides some generic mapping functions.
 */
public interface MapperSupport {

    default String map(CharSequence value) {
        return value == null ? null : value.toString();
    }

}
