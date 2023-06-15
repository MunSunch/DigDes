FROM openjdk:18.0.2.1-slim-buster
WORKDIR /rest-app/system_project
COPY ./application/target/SystemProjects-exec-jar-with-dependencies.jar ./
CMD java -jar SystemProjects-exec-jar-with-dependencies.jar