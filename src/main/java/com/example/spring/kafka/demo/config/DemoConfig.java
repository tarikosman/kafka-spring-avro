package com.example.spring.kafka.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * This automatically creates a topic on kafka. For productive systems, it is
 * suggested to manually create the topics, because this allows to adjust them
 * during runtime, according to the changing requirements.
 */
@Configuration
@EnableScheduling
public class DemoConfig {
    public static final String TOPIC_NAME_DB_DEMO = "person-changes";

    /**
     * Topics can be created automatically by the application, instead of using the
     * "kafka-topics --create" command.
     * 
     * @return the topic which is created, if it doesn't exist
     */
    @Bean
    public NewTopic topicDbDemo() {
        return TopicBuilder.name(TOPIC_NAME_DB_DEMO)
                .partitions(10)
                // .replicas(2) // only possible if more than one node is started
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(ErrorProperties.class)
    @ConfigurationProperties(prefix = "server.error")
    ErrorProperties errorProperties() {
        return new ErrorProperties();
    }
}
