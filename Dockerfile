#FROM bellsoft/liberica-openjdk-centos:11
#FROM amd64/eclipse-temurin:11-alpine

FROM openjdk:11
ADD /target/testinside-0.0.1-SNAPSHOT.jar testinside1.jar
ENTRYPOINT ["java", "-jar", "testinside1.jar"]

