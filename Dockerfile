FROM amd64/eclipse-temurin:11-alpine

ADD /target/testinside-0.0.1-SNAPSHOT.jar testinside1.jar
ENTRYPOINT ["java", "-jar", "testinside1.jar"]

