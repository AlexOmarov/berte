# Part 1: Build the app using Maven
FROM maven:latest

## download dependencies
COPY api/pom.xml api/pom.xml
ADD pom.xml /
## build after dependencies are down so it wont redownload unless the POM changes
COPY api/src api/src
ADD . /

WORKDIR api
RUN mvn verify clean install
RUN mvn package

WORKDIR .
RUN mvn verify clean install
RUN mvn package

# Part 2: use the JAR file used in the first part and copy it across ready to RUN
FROM openjdk:19-jdk-alpine3.14

RUN adduser --disabled-password -g root -u 1001 docuser

USER docuser

WORKDIR /home/docuser

## COPY packaged JAR file and rename as app.jar
## → this relies on your MAVEN package command building a jar
## that matches *-jar-with-dependencies.jar with a single match
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","./app.jar"]