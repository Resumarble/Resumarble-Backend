FROM --platform=linux/amd64 openjdk:17-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Seoul","/app.jar"]
