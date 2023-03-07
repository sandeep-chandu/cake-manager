FROM eclipse-temurin:17-jdk-alpine
RUN echo "$PWD"
VOLUME /tmp
RUN echo "$PWD"
COPY target/cake-manager-1.0-SNAPSHOT.jar cake-manager-1.0.jar
ENTRYPOINT ["java","-jar","/cake-manager-1.0.jar"]
