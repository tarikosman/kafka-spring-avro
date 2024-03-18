#!/bin/bash

# Conduktor-platform:$DOCKER_HOST_IP:8080
# Single Zookeeper: $DOCKER_HOST_IP:2181
# Single Kafka: $DOCKER_HOST_IP:9092
# Kafka Schema Registry: $DOCKER_HOST_IP:8081
# Kafka Rest Proxy: $DOCKER_HOST_IP:8082
# Kafka Connect: $DOCKER_HOST_IP:8083
# KSQL Server: $DOCKER_HOST_IP:8088
# (experimental) JMX port at $DOCKER_HOST_IP:9001

docker compose -f docker-compose.yml up $*