FROM bellsoft/liberica-openjdk-centos:11
ADD /target/testinside-0.0.1-SNAPSHOT.jar testinside.jar
ENTRYPOINT ["java", "-jar", "testinside.jar"]

