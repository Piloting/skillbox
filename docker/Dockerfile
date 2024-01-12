FROM openjdk:17-jdk-alpine
MAINTAINER suntsovto
COPY ../target/users-0.0.1-SNAPSHOT.jar users-app.jar
ENTRYPOINT ["java","-jar","/users-app.jar"]
# run: docker image build -t pilot_repo/user_app:1.0 -f docker/Dockerfile .