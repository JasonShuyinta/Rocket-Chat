FROM openjdk:11 as build
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} rocket-chat-app.jar
ENTRYPOINT ["java","-jar","/rocket-chat-app.jar"]