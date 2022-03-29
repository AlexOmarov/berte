FROM openjdk:19-jdk-alpine3.14

RUN adduser --disabled-password -g root -u 1001 docuser

USER docuser

WORKDIR /home/docuser

## COPY packaged JAR file and rename as app.jar
## → this relies on your MAVEN package command building a jar
## that matches *-jar-with-dependencies.jar with a single match
COPY /target/*.jar app.jar
ENTRYPOINT ["java","-jar","./app.jar"]