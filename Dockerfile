FROM openjdk:19-alpine
COPY target/note-0.0.1-SNAPSHOT.jar note-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/note-0.0.1-SNAPSHOT.jar"]
