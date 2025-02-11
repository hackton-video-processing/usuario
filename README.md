# Hackaton - Sistema de Upload e Download

O sistema tem como intuito orquestrar as chamadas do upload, processamento e download de um video.

## Integrantes do Grupo
- RM354032 - Alysson Gustavo Rodrigues Maciel
- RM355969 - Vinicius Duarte Mendes Nepomuceno
- RM354090 - Lucas Pugliese de Morais Barros
- RM353273 - Felipe Pinheiro Dantas

## Postman para realizar os testes
Dentro do Projeto no diretório "postman" há um arquivo com uma collection postman com todas as rotas mapeadas para teste.
```
./postman/ClientFiap.postman_collection.json
```

## Realizar cadastro de client/usuario, autenticar e validar.

Para iniciar o registro do cliente segue abaixo o request:

```url
POST http://localhost:8080/api/v1/auth/register

{ 
    "firstname":"Joao",
    "lastname":"Barros",
    "cpf":"12345678905",
    "email":"email@gmail.com",
    "password":"password1"
}
```

A resposta será o token com informações do cliente, data de expiração, claims e etc

```url
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MzkyMzY1NzEsImV4cCI6MTczOTIzODAxMX0.Wal_vdtIWA_0eIgn7feeinHguULMv5iqARhgEB9D2gw"
}
```

## Para realizar login 

```url
POST http://localhost:8080/api/v1/auth/authenticate
```

O retorno da consulta de processamento será:

```url
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MzkyMzY3MDIsImV4cCI6MTczOTIzODE0Mn0.eNnVctqXt71hZlF-5XUdPxZu_DwFoJ-Xu_l48i38ujE"
}
```

## Para validação do token (Token Introspection)

```url
POST http://localhost:8080/api/v1/auth/validate
```