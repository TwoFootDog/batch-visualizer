FROM openjdk:8-jdk
VOLUME /tmp
ADD ./build/libs/batch-visualizer-auth-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${ENV1}","-Duser.timezone=KST","/app.jar"]
