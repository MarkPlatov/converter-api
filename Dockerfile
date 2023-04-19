FROM openjdk:17
MAINTAINER mark
COPY target/converter-api-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application-dockerdefault.yaml application.yaml
ENTRYPOINT ["java","-jar","/app.jar"]