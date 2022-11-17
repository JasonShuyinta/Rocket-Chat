FROM openjdk:11 as build
COPY target/*.jar rocket-chat-app.jar
ENTRYPOINT ["java","-jar","/rocket-chat-app.jar"]
