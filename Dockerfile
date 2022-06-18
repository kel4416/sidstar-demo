FROM openjdk:18
EXPOSE 8080
ADD target/sidstar-docker.jar sidstar-docker.jar
ENTRYPOINT ["java","-jar","sidstar-docker.jar"]