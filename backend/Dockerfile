FROM maven:3.3-jdk-8 as builder
COPY . /usr/src/todo
WORKDIR /usr/src/todo

RUN mvn package -f /usr/src/todo 

EXPOSE 8080:8080
ENTRYPOINT ["java","-jar","/usr/src/todo/target/todo-api-0.0.1.jar"]