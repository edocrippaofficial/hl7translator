FROM openjdk:18-jdk-slim

ARG COMMIT_SHA=<not-specified>

WORKDIR /home/java

RUN adduser --disabled-password --shell /sbin/nologin --ingroup root java
RUN mkdir app
RUN echo "hl7translator: $COMMIT_SHA" >> ./app/commit.sha

COPY build/libs/application.jar ./application.jar

#TODO demo configmap. To be removed...
COPY translationMap.json ./

USER java
CMD ["java","-jar", "./application.jar"]
