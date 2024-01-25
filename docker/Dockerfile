FROM openjdk:17-jdk-alpine
MAINTAINER suntsovto
COPY ../target/users-0.1.0-SNAPSHOT.jar users-app.jar
ENTRYPOINT ["java","-jar","/users-app.jar"]
# run from root dir: docker image build -t pilot_repo/user-app:0.1.0 -f docker/Dockerfile .