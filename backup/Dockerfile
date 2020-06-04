FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/batch-visualizer-backend-migrator-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENV ENV1=dev
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${ENV1}","/app.jar"]
