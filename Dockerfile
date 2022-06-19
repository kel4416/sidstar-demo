FROM openjdk:18
EXPOSE 8080
EXPOSE 80
EXPOSE 443
ADD target/sidstar-docker.jar sidstar-docker.jar
ENTRYPOINT ["java","-jar","sidstar-docker.jar"]