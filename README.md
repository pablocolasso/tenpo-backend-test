# Tenpo backend test

![](https://img.shields.io/badge/build-success-brightgreen.svg)

![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/java_8-✓-blue.svg)
![](https://img.shields.io/badge/postgrestsql-✓-blue.svg)
![](https://img.shields.io/badge/docker-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)
![](https://img.shields.io/badge/swagger_2-✓-blue.svg)
![](https://img.shields.io/badge/maven-✓-blue.svg)
-------------------
## Description

This application has the following capabilities:
- Create an user and manage logging session.
- Generate a jwt that allows the user to perform operations like sum or get the history of all the transactions

-------------------
## Database

This application use a postgreSQL db.

-------------------

## How to run locally

The app runs with java 8, postgreSQL and use a docker image. 

      -https://docs.docker.com
      -https://maven.apache.org

Clone the repository from git

```
   git@github.com:pablocolasso/tenpo-backend-test.git
```

Install maven dependencies 

```
  mvn clean install
```

To run the app please execute the following command line. Note: you have to run from the main path where docker-compose is located.
```
  docker-compose up
```

----------

## Insomnia/postman collection
Attached on te email is the json collection to test the application. Note: when we use the jwt in the x-auth-token you should use like this e.g. "Baerer token". Replace the token after the space. 
```
  http://localhost:8080/swagger-ui/#/
```
