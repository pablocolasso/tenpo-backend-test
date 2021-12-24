FROM openjdk:8-jdk-alpine
ADD target/tenpo-backend-test-1.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]