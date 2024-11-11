FROM openjdk:18-jdk-slim

ARG COMMIT_SHA=<not-specified>

LABEL maintainer="https://github.com/edocrippaofficial" \
  name="HL7Translator" \
  description="A simple HTTP server to translate HL7 messages into custom JSON"

WORKDIR /home/java

RUN adduser --disabled-password --shell /sbin/nologin --ingroup root java
RUN mkdir app
RUN echo "hl7translator: $COMMIT_SHA" >> ./app/commit.sha

COPY build/libs/application.jar ./application.jar

USER java
CMD ["java","-jar", "./application.jar"]
