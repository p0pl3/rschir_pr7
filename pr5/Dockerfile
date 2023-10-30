FROM gradle:latest AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build
FROM openjdk:17
COPY --from=builder /home/gradle/src/build/libs/pr5-1.0.0.jar /app/pr5.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/pr5.jar"]