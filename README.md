# Hackaton - MicroService responsible for authenticate and authorization

## Development group
- RM354032 - Alysson Gustavo Rodrigues Maciel
- RM355969 - Vinicius Duarte Mendes Nepomuceno
- RM354090 - Lucas Pugliese de Morais Barros
- RM353273 - Felipe Pinheiro Dantas

## Postman to make the tests locally
Inside of the project there is a postman's file with all the endpoints to make the local test 

```
./postman/ClientFiap.postman_collection.json
```

## To register the new user.

Request responsible to create a new user and return token in the response;

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

That is the response of the register endpoint with information of the client, expiration date of the token and others claims;

```url
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MzkyMzY1NzEsImV4cCI6MTczOTIzODAxMX0.Wal_vdtIWA_0eIgn7feeinHguULMv5iqARhgEB9D2gw"
}
```

## Make login after the registration

```url
POST http://localhost:8080/api/v1/auth/authenticate
{ 
    "email":"email@gmail.com",
    "password":"password1"
}
```

Response of the login:

```url
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MzkyMzY3MDIsImV4cCI6MTczOTIzODE0Mn0.eNnVctqXt71hZlF-5XUdPxZu_DwFoJ-Xu_l48i38ujE"
}
```

## To validate the token (Token Introspection)

```url
POST http://localhost:8080/api/v1/auth/validate
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MzkyMzY3MDIsImV4cCI6MTczOTIzODE0Mn0.eNnVctqXt71hZlF-5XUdPxZu_DwFoJ-Xu_l48i38ujE"
}
```