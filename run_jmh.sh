#!/bin/bash

# Compose image
docker compose -f docker/Linux-JDK21/docker-compose.yml up --build -d

# Run JMH inside container
docker exec -it linux-jdk21-cantaloupe-1 mvn --batch-mode test -Pbenchmark > out.txt

# Once done, kill processes
docker kill linux-jdk21-cantaloupe-1
docker kill linux-jdk21-minio-1
docker kill linux-jdk21-redis-1
