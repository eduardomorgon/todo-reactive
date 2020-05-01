# Lista de Tarefas

Uma lista de tarefas construida com uma arquitetura reativa.

## Necessário 
Para executar a aplicação você ira precisar de:
- [Docker Compose]([https://docs.docker.com/compose/gettingstarted/](https://docs.docker.com/compose/gettingstarted/))

## Executar a aplicação localmente

Para executar a aplicação localmente será necessário o Docker + Docker Compose, e abrir um terminal na raiz da aplicação e executar o comando:
```shell
docker-compose up --build 
```
A aplicação será executada no docker, obedecendo a ordem de construção configurado no arquivo **docker-compose.yml**. Após o deploy da aplicação, ela estará disponivel na url [http://localhost](http://localhost).

## Ferramentas 

Esse exemplo foi criado usando o conceito de aplicação reativa. Fazendo o uso de **Event Stream** para listar as tarefas.
Foi ultilizado as seguintes ferramentas:

* MongoDB
* Spring Boot Webflux
* Spring Data Reactive
* Swagger 
* Lombok
* Spring Actuator
* Angular
* Docker
* Docker-Compose

