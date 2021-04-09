# SouthSystemTest

#About

Sistema Bancário

## Create mongoDB environment

Run following commands to create mongoDb wth replicaSet:

- sudo docker pull mongo:4.0.4
- sudo docker run -d -p 27017-27019:27017-27019 --name SouthSystemTestDb mongo:4.0.4

## Useful Link

- Link to access the Swagger documentation http://localhost:8080/swagger-ui.html#
- Import file Archive.postman_collection.json to your Postman for valid requests

```
MongoDB configuration example link:
https://www.thepolyglotdeveloper.com/2019/01/getting-started-mongodb-docker-container-deployment/

MongoDB access tool:
https://robomongo.org/
```

## Run Aplication.java and type http://localhost:8080/ in your browser :)