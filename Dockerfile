FROM ubuntu:lastet AS build

RUN apt-get update

RUN apt install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y

EXPOSE 8080

COPY --from=build  /target/todolist-1.0.0.jar app.jar


ENTRYPOINT [ "java","-jar","app.jar" ]