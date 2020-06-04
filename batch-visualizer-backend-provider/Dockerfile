FROM openjdk:8-jdk
VOLUME /tmp
ADD ./build/libs/batch-visualizer-backend-provider-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENV ENV1=dev
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${ENV1}","-Duser.timezone=KST", "/app.jar"]
 