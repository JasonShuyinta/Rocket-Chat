FROM openjdk:11 as build
COPY --from=build target/*.jar rocket-chat-app.jar
ENTRYPOINT ["java","-jar","/rocket-chat-app.jar"]
