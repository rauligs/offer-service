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

## Offer

## API overview
The API is RESTful and content will be in JSON

### Resource components

* Offers: An offer is a proposal to sell a specific product or service under specific conditions. Fields
** *description:* Friendly description
** *currency:* [Currency code](https://en.wikipedia.org/wiki/ISO_4217)
** *amountInPence:* Amount in pence of the given currency
** *startDate:* Start date of the offer
** *endDate:* End date of the offer
** *status:* Current status of the offer. Possible values are:
*** CREATED: Offer has been created (endDate > now)
*** EXPIRED: Offer has expired (endDate < now)

| resource      | method   |description                       |
|:--------------|:---------|:---------------------------------|
| `/offers`     |   POST   | returns response with status 201 (CREATED) and Location header of the entity created (can't be fetched) |
| `/offers/{id}`|   GET    | returns response with status 200 (OK) along with the offer  if the offer exists, otherwise 404 (NOT FOUND)|


#### POST /offers Request example
```json
{
 "description":"great offe for great people"
 "currency":"GBP",
 "amountInPence":12345,
 "startDate":"2018-04-1 22:10",
 "endDate":"2018-05-1 22:10"
}
```



#### GET /offers/{id} Response example
```json
{
 "description" : "buy 2 get 1!",
 "currency":"GBP",
 "amountInPence":12345,
 "startDate":"2018-04-1 22:10",
 "endDate":"2018-05-1 22:10",
 "status":"CREATED"
}
```

### Date formatting
_Date format is 'yyyy-MM-ddTHH:mm:00Z' in UTC_
