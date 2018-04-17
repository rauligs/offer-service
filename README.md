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
* JPA - Hibernate with an [embedded database (H2)](http://www.h2database.com/html/main.html)

## Build

### Build + assemble
The following command runs the tests and will assemble the application (jar):
`./gradlew clean build`

### Run

* Upon successful build, the jar will be assembled in `build/libs/offer-service-rest-api-0.1.0.jar`
* Run assembled application artifact: `java -jar build/libs/offer-service-rest-api-0.1.0.jar`
* Run the app without building the jar: `./gradlew bootRun`

## Offer

## API overview
The API is RESTful and content will be in JSON

### Resource components

*Offers:* An offer is a proposal to sell a specific product or service under specific conditions. Fields
* *description:* Friendly description
* *currency:* [Currency code](https://en.wikipedia.org/wiki/ISO_4217)
* *amountInPence:* Amount in pence of the given currency
* *startDate:* Start date of the offer
* *endDate:* End date of the offer
* *status:* Current status of the offer. Possible values are:
  1. CREATED: Offer has been created (endDate > now)
  2. EXPIRED: Offer has expired (endDate < now)
  3. CANCELLED: Offer has been cancelled

| resource             | method   |description                       |
|:---------------------|:---------|:---------------------------------|
| `/offers`            |   POST   | returns response with status 201 (CREATED) and Location header of the entity created (can't be fetched) |
| `/offers/{id}`       |   GET    | returns response with status 200 (OK) along with the offer  if the offer exists, otherwise 404 (NOT FOUND)|
| `/offers/{id}/cancel`|   POST   | returns response with status 200 (OK), 404 (NOT FOUND) or 409 (CONFLICT) if the offer is in EXPIRED status|


#### POST /offers Request example
```json
{
 "description":"great offer for great people",
 "currency":"GBP",
 "amountInPence":12345,
 "startDate":"2018-04-1 22:10",
 "endDate":"2018-05-1 22:10",
 "status":"CREATED"
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
 "status":"EXPIRED"
}
```

### Date formatting
_Date formatting is 'yyyy-MM-ddTHH:mm:00Z' in UTC_

## Notes about the assignment

This is an *unfinished solution,* this is just done to cover the requirements and it is left a substantial refactoring but at least
is now a good place to be but this test is quite large and takes a considerable amount of work:

* *Offer lifecycle.* Is clear that this now needs to be refactored, in my opinion it would be good to create a new table to represent
Offer events. Offer will keep all the core data ad the events will register the changes on the states / fields that changes, so this
would be the source of truth of the state of the offers, it means removing all the work in progress done regarding statuses.
* Status as Enums, also it could be potentially be introduced a state machine that enforces the state transitions
(plus is a good source of documentation).
* _currency_ to be renamed as _currencyCode_ for clarification. Also this should be at least an enum
* _amountInPence_ was a choice for now since there are not any calculation and no boundaries set, so assuming 2 digits for decimal I
think is sufficient for now.
* OfferDTO could be split (Request/Response) since requests does not contain `status` now it could growth to contains segregated data.
* For sure there are more things I would change (Dirties context per method not ideal, other magic number/strings to extract, validations,
Http headers, error messages, HAL representation, etc), but at least Offer Service is in a good place to be refactored / improved / maintained.
* Testing. It might be a bit over-tested on the integration tests side but this should not be the case with contract testing in place and also
end to end testing being part of other set of tests outside of the boundaries of this service. Regarding unit testing I would add testing more the times boundaries.


#### Thanks for your time!
