# Offer Service

## Assignment

You are required to create a simple RESTful software service that will
allow a merchant to create a new simple offer. Offers, once created, may be
queried. After the period of time defined on the offer it should expire and
further requests to query the offer should reflect that somehow. Before an offer
has expired users may cancel it.

## Tech Stack

* Build automation: [Gradle](https://gradle.org/)
* RESTful [Spring](https://docs.spring.io/spring/docs/5.0.5.RELEASE/spring-framework-reference) base application
(conveniently using [Spring Boot](https://projects.spring.io/spring-boot/))
* Testing and validating REST services in Java with [Rest Assured](http://rest-assured.io/) in favour of traditional RestTemplate (simple + nice dsl)
* JPA - Hibernate with an embedded database (H2)

## Building and Assemble

### Build and Assemble
The following command runs the tests and will assemble the application (jar):
`./gradlew clean build`

### Run

Upon successful build, the jar will be assembled in `build/libs/offer-service-rest-api-0.1.0.jar`
Run assembled application artifact: `java -jar build/libs/offer-service-rest-api-0.1.0.jar`
Run the app without building the jar: `./gradlew bootRun`

## API overview
The API is RESTful and content will be in JSON

### Resource components

| resource      | method   |description                       |
|:--------------|:---------|:---------------------------------|
| `/offers`     |   POST   | returns response with status 201 (CREATED) and Location header of the entity created (can't be fetched) |
| `/offers/{id}`|   GET    | returns response with status 200 (OK)  if the offer exists, otherwise 404 (NOT FOUND)|
