FROM openjdk:8-slim as build

MAINTAINER https://github.com/maxlovenheim

COPY target/iban-0.0.1-SNAPSHOT.jar iban-0.0.1-SNAPSHOT.jar

#execute the application
ENTRYPOINT ["java","-jar","/iban-0.0.1-SNAPSHOT.jar"]