## Test task for a paid internship

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

#### Users
```bash
# Get
curl 'http://localhost:8080/api/users'
curl 'http://localhost:8080/api/users/0'
curl 'http://localhost:8080/api/users/search/by-email?email=admin@gmail.com'

# Create
curl -sid '{"name":"New User","email":"user@local.loc", "password":"12345", "roles" : ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/users

# Update
curl -siX PUT -d '{"name":"User 4","email":"user4@local.loc", "password":"12345", "roles" : ["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/users/4

# Delete
curl -siX DELETE http://localhost:8080/api/users/2
```

#### Restaurant
```bash
# Get
curl http://localhost:8080/api/restaurants
curl http://localhost:8080/api/restaurants/0
curl http://localhost:8080/api/restaurants/search/by-name?name=McDonalds

# Create
curl -sid '{"id":"","name":"Теремок"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants
curl 'http://localhost:8080/api/restaurants' -i -d '{"name":"Teremok"}' -H 'Content-Type:application/json;charset=UTF-8'
curl 'http://localhost:8080/api/restaurants' -i -d '{"name":"Теремок"}' -H 'Content-type: application/hal+json;charset=UTF-8'
curl "http://localhost:8080/api/restaurants" -i -d @data/restaurant.json -H "Content-type:application/json;charset=UTF-8"

# Update
curl -siX PUT -d '{\"name\":\"Васаби\"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/api/restaurants/3

# Delete
curl -siX DELETE http://localhost:8080/api/restaurants/4
```


