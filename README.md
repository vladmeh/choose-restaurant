[![Build Status](https://travis-ci.org/vladmeh/choose-restaurant.svg?branch=master)](https://travis-ci.org/vladmeh/choose-restaurant)

Restaurant system of choice, something to eat lunch (REST only)
===============================================================
### [Testing task](https://github.com/vladmeh/choose-restaurant/blob/master/test_task.md)

#### Implementation Stack:

* Spring Boot 2.0.2
* Spring Data JPA
* Spring Data REST
* Spring HATEOAS
* Spring Security
* H2 Database

Installation
------------
```console
$ git clone https://github.com/vladmeh/choose-restaurant.git
```
Introduction
------------
#### Production environment
```console
$ mvn spring-boot:run
``` 
or
```console
$ mvn clean package
$ java -Dfile.encoding=UTF8 -jar target/choose-restaurant.jar
```

##### H2 database
* JDBC URL: jdbc:h2:file:~/choosing
* user name: sa
* password: WdyHMa4G

#### Dev environment
```console
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev
``` 
or
```console
$ mvn clean package
$ java -Dspring.profiles.active=dev -Dfile.encoding=UTF8 -jar target/choose-restaurant.jar
```

##### H2 database
* [console](http://localhost:8080/console) - `http://localhost:8080/console`
* driver class: org.h2.Driver
* JDBC URL: jdbc:h2:mem:choosing
* user name: sa
* password: no password

##### HAL browser
* http://localhost:8080/api

#### Authentication
* Admin 
    * login: "admin@gmail.com"
    * Password: "admin"
    * Request Headers: "Authorization:Basic YWRtaW5AZ21haWwuY29tOmFkbWlu"


- User 
    - login: "user@yandex.ru"
    - Password: "user"
    - Request Headers: "Authorization:Basic dXNlckB5YW5kZXgucnU6dXNlcg=="

Run
---
### Admin
##### CURL Users
```console
$ curl 'http://localhost:8080/api/users' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl 'http://localhost:8080/api/users/0' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl 'http://localhost:8080/api/users/search/by-email?email=admin@gmail.com' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

$ curl -si 'http://localhost:8080/api/users' -d '{"name":"New User","email":"user@local.loc", "password":"12345", "roles" : ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/users/0' -X PUT -d '{"name":"User update","email":"user@yandex.ru", "password":"12345", "roles" : ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/users/0' -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
```

##### CURL Restaurant
```console
$ curl http://localhost:8080/api/restaurants -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/restaurants/0 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/restaurants/search/by-name?name=McDonalds -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

$ curl -si 'http://localhost:8080/api/restaurants' -d '{"name":"Mama Roma"}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/restaurants/0' -X PUT -d '{"name":"Teremok"}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/restaurants/0' -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
```

##### CURL Menu
```console
$ curl http://localhost:8080/api/menu -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/menu/0 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/menu/search/by-date?date=2018-05-23 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/menu/search/by-restaurant?restaurant=http://localhost:8080/api/restaurant/0 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

$ curl -si 'http://localhost:8080/api/menu' -d '{"date": "2018-06-05", "restaurant":"http://localhost:8080/api/restaurant/0"}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/menu/0' -X PUT -d '{"date": "2018-06-04"}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/menu/0' -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
```

##### CURL Dishes
```console
$ curl http://localhost:8080/api/dishes -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/dishes/0 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/dishes/search/by-date?date=2018-05-23 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl http://localhost:8080/api/dishes/search/by-menu?menu=http://localhost:8080/api/menu/0 -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'

$ curl -si 'http://localhost:8080/api/dishes' -d '{"name": "Kebab", "price":"200", "menu":"http://localhost:8080/api/menu/0"}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/dishes/0' -X PUT -d '{"name": "Big lunch", "price" : 300}' -H 'Content-Type:application/json;charset=UTF-8' -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
$ curl -si 'http://localhost:8080/api/dishes/0' -X DELETE -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu'
```

## Choosing
Choice for menu 0:
```console
$ curl -si 'http://localhost:8080/api/choice/0' -X POST -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6dXNlcg=='
```

Choice for menu 2:
```console
$ curl -si 'http://localhost:8080/api/choice/2' -X POST -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6dXNlcg=='
```

Check current choice:
```console
$ curl -si 'http://localhost:8080/api/choice' -H 'Authorization:Basic dXNlckB5YW5kZXgucnU6dXNlcg==
```

## Testing
* Postman - `./choose-restaurant.postman_collection.json`


