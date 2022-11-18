FROM openjdk:11 as build
COPY target/*.jar rocket-chat-app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/rocket-chat-app.jar"]
