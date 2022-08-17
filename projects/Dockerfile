FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-debian
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]