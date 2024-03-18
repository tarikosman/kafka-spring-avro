package com.example.spring.kafka.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This application, shows the integration of an avro schema registry with
 * spring-boot (without using spring-cloud-stream).
 * In many cases, you'll want to use debezium, which provides an implementation
 * of the outbox-pattern, decoupling the transmission to kafka. In contract to
 * this example, Debezium would still work correctly, if the Kafka cluster would
 * be temporarily unreachable.
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
