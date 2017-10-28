# Database
Set up MySQL on 3316 port and run it.

# Migration scripts
`application.properties` contains `flyway.enabled` flag.
To run migration scripts it should be set to `true`.

# API

## User
### Create 
POST /users
```
{
	"lastName": "Watson",
    "firstName": "Emma",
    "userName": "watsone",
    "email": "emma@email.com",
    "permissions": [{
    	"name": "expenseViewer"
    }]
}
```

### Retrieve
GET /users/{userName}

### Delete
DELETE /users/{userName}


## Incomes
### Create 
POST /incomes?user={userName}
```
{
    "amount": 100.13,
    "type": "salary"
}
```


## Expenses
### Create 
POST /expenses?user={userName}
```
{
  "amount": 30.15,
  "group": "House",
  "comment": "toothpaste"
}
```

### Retrieve for user
GET /expenses?user={userName}

### Delete by id
DELETE /expenses/{id}
