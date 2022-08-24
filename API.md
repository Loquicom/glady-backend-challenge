# API definitions

Lists of all APIs available on the server

------



## Time API

The Time API allow you to change the date used by the server. It was only designed for testing purposes to allow easy testing of deposit expiration.



### Get Time

Retrieves the current date for the server

**Request**

```http
GET http://localhost:8888/api/time
```

**Result**

```
Wed Aug 24 00:00:00 GMT 2022
```



### Set Time

Change the server date

**Request**

```http
PUT http://localhost:8888/api/time/year/{year}/month/{month}/day/{day}
```

**Result**

```
Wed Aug 24 00:00:00 GMT 2022
```

------



## Company API

Creation and retrieval of company information



### Get Company

**Request**

```http
GET http://localhost:8888/api/companies/{id}
```

**Result**

```json
{
    "id": 1,
    "name": "Systems Alliance",
    "balance": 100
}
```



### Create company

**Request**

```http
POST http://localhost:8888/api/companies
```

**Body**

```json
{
    "name": "Systems Alliance",
    "balance": 100
}
```

**Result**

```json
{
    "id": 1,
    "name": "Systems Alliance",
    "balance": 100
}
```



### Credit company

**Request**

```http
PUT http://localhost:8888/api/companies/{id}/credit
```

**Body**

```json
{
    "amount": 123
}
```

------



## Employee API

Creation and retrieval of employee information



### Get Employee

**Request**

```http
GET http://localhost:8888/api/employees/{id}
```

**Result**

```json
{
    "id": 1,
    "firstname": "Jane",
    "lastname": "Shepard",
    "company_id": 1
}
```



### Create employee

**Request**

```http
POST http://localhost:8888/api/employees
```

**Body**

```json
{
    "firstname": "Jane",
    "lastname": "Shepard",
    "company_id": 1
}
```

**Result**

```json
{
    "id": 1,
    "firstname": "Jane",
    "lastname": "Shepard",
    "company_id": 1
}
```

------



## Deposit API

API that allows you to add different types of deposits to an employee, to retrieve a particular deposit or to sum the employee balance

### 

### Get Deposit

**Request**

```http
GET http://localhost:8888/api/deposits/{id}
```

**Result**

```json
{
    "id": 1,
    "amount": 80,
    "date": "2000-08-21T00:00:00.000+00:00",
    "expire": "2001-08-21T00:00:00.000+00:00",
    "type": "GIFT",
    "employee_id": 1
}
```



### Create Deposit

Only `GIFT` and `MEAL` are allowed in type field 

**Request**

```http
POST http://localhost:8888/api/deposits
```

**Body**

```json
{
    "id": 2,
    "amount": 80,
    "date": "2022-08-24T20:24:31.321+00:00",
    "expire": "2023-08-24T00:00:00.000+00:00",
    "type": "GIFT",
    "employee_id": 1
}
```

**Result**

```json
{
    "id": 1,
    "firstname": "Jane",
    "lastname": "Shepard",
    "company_id": 1
}
```



### Get Employee Balance

**Request**

```http
GET http://localhost:8888/api/deposits/employees/{id}/balance
```

**Result**

```json
{
    "id": 1,
    "firstname": "Jane",
    "lastname": "Shepard",
    "balance": 80,
    "company_id": 1
}
```

