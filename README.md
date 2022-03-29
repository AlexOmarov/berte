# Berte

Backend server of Transpodia game.

## Table of Contents
- [Introduction](#introduction)
- [Documentation](#documentation)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [API](#requirements)

## Introduction
Berte is responsible for processing all client's data on backend.  
Processing means getting, storing, analyzing, creating new data

## Documentation
All the service's documentation can be found in `doc` folder
There you can find:

- Data model
- Class diagram
- Sequence diagram for all the flows which are performed by service
- Description of all service integrations
- Descriptions of service APIs, incoming and outcoming esb events
- Some additional diagrams for better understanding of underlying processes

## Features
 * Connection between clients and server
 * Storing actual clients data
 * Creating maps for clients
 * Sending actual info to clients
 * Rsocket endpoint with Hessian-coded payload

## Requirements
The application can be run locally or in a docker container, 
the requirements for each setup are listed below.

### Local
* [Java 17 SDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* [Maven >= 3.8](https://maven.apache.org/install.html)
* [Postgres >= 13](https://hub.docker.com/_/postgres)

### Docker
* [Docker](https://www.docker.com/get-docker)

## Quick Start
Application will run by default on port `11006`. Rsocket port will be `7000`

Configure the port by changing `server.port` in __application.properties__
Configure the rsocket port by changing `spring.rsocket.server.port` in __application.properties__


### Run Local

Depending on which environment you want to launch the service you should choose spring profile:
* To launch service in local env, user `local` profile
* To launch service in dev contour, user `dev` profile
* To launch service in fxb2 contour, user `fxb2` profile

You can run application either via Intellij launch configuration (preferred way) or
manually
```bash
$ <maven command> --args='--spring.profiles.active=local'
```

### Run Docker
Before launching, it is needed to build child module `api` and parent module `berte`
```
mvn verify clean install
mvn package
```
Then, use `docker-compose.yml` to build the image and create a container.
Note, that by default container will run using `application-dev.properties`
Also, it is needed to add following env properties:
```properties
POSTGRES_USER=berte;POSTGRES_PASSWORD=berte;
```
Also, there may be a problem with new lines in properties file when trying to launch docker compose
It is needed to disable `Use Docker Compose V2` in docker engine

### Run code quality assurance tasks

If you want to get total coverage and sonar analysis with local changes, then you should run following tasks:
```
./mvnw clean verify test
./mvnw clean verify sonar:sonar -D'sonar.host.url'=<HOST> -D'sonar.login'=<PROJECT_TOKEN>
```
Then, jacoco test report with coverage will be generated on local machine in build folder
and sonar analysis will take place on server and will be visible on sonarcloud instance.
Also, it is recommended to install [SonarLint](https://plugins.jetbrains.com/plugin/7973-sonarlint) 
Intellij plugin for integration of code quality analysis more native-like
Also, there is a possibility to configure jacoco coverage as a replacement for common Idea coverage
analyzer (it's optional)

## API

### Web endpoints
All the service's web endpoints specification can be found in `doc` folder
### Esb events
All the service's events specification can be found in `doc` folder
