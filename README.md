# Wedoogift Backend challenge

[![Build Status](https://ci.loquico.me/api/badges/Loquicom/glady-backend-challenge/status.svg)](https://ci.loquico.me/Loquicom/glady-backend-challenge)

You are interested in joining our team ? try to accomplish this challenge, we will be glad to see
your code and give you feedback.

## Guidelines

* Use Java to accomplish this challenge.
* Clone this repo (do not fork it)
* Only do one commit per level and include the `.git` when submitting your test
* We are not expecting any user interface for this challenge.

## Evaluation

We will look at:

* How you use OOP.
* Your code quality.
* The design patterns you use.
* Your ability to use unit tests.

## Statements

Companies can use Wedoogift services to distribute:

- Gift deposits
- Meal deposits

### Gift deposits

Gift deposits has 365 days lifespan, beyond this period it will no longer be counted in the user's balance.

example:
John receives a Gift distribution with the amount of $100 euros from Tesla. he will therefore have $100 in gift cards in his account.
He received it on 06/15/2021. The gift distribution will expire on 06/14/2022.

### Meal deposits

Meal deposit works like the Gift deposit excepting for the end date. In fact meal deposits expires at the end of February of the year
following the distribution date.

example:
Jessica receives a Meal distribution from Apple with the amount of $50 on 01/01/2020, the distribution ends on 02/28/2021.

* Implement one or two functions allowing companies to distribute gift and meal deposits to a user if the company balance allows it.
* Implement a function to calculate the user's balance.

### How to run and test

The test called `appTest` in the `GladyBackendApplicationTest` class executes all the instructions requested in the statement (create a
company and an employee, deposit money if the company has the funds and display the employee's balance).
All actions are displayed in the application logs and asserts verify that the scenario is running correctly.

It is otherwise also possible to launch the application (with or without docker, all the configuration is already done) to test the
application
through the REST API it exposes.
The API documentation is available in the [API.md](./API.md) file, and in a Postman configuration file
called `Glady.postman_collection.json`
The Postman collection is intended to work with the configuration defined in the docker-compose

A file named `run.sh` is available to manage the build and execution of the application, here are the main commands:

* `run.sh build_start`: Build application (with maven) and docker image, then start the docker (if the application is running stop the
  containers first)
* `run.sh build`: Build application (with maven) and docker image
* `run.sh start`: Start containers
* `run.sh stop`: Stop containers
* `run.sh test`: Execute all tests
* `run.sh purge`: Remove all containers and volumes
* `runs.sh reset`: Equivalent to purge, then build_start

### Known issue

Sometimes during the first startup the database container does not have time to finish its initialization before the server container
starts, which causes a connection error to the database. In this case you just have to restart the containers (stop then start) 
