#!/bin/sh
mvn clean install -DskipTests=true assembly:single
docker compose -f docker-compose.yml up