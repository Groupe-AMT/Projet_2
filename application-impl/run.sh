#!/bin/bash
cd ../Docker
docker-compose up --build
cd ..
mvn spring-boot:run
