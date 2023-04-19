# converter-api

Microservice for integration with **coinmarket-api** (see https://github.com/MarkPlatov/coinmarket-api)

## Databases:

Implemented database storage using Hibernate.

Migration tool - `liquibase`

* `PostgreSQL` - runtime
* `H2` - tests

## Swagger

Endpoint: http://localhost:8080/swagger-ui/index.html

## REST endpoints:

* GET `/exchange/find` - Find currencies description by it's name part
* GET `/exchange/` - Exchange an amount of source currency to destination currency.

_**NOTE:**_ _Integer part and Fractional part of amount are separate
(if amount is `12.01` then Integer part is `12` and Fractional part is `01`).
This solution allow to avoid problems caused incorrect floating numbers
processing in different programming languages. Also Integer part and Fractional part
are actually Strings. So it is not a problem if actual amount is bigger than `Long.MAX_VALUE`_

## ActiveMQ topics:

_**NOTE:**_ _ActiveMQ works in request-response mode._

* out (generate request):
    * `exchange.rate.request`
    * `coin.request`
* in (immediately get response):
    * `exchange.rate.response`
    * `coin.response`

`exchange.rate.` - to get exchange rate by currencies cmc id
(at first search requested exchange rate. If it is not found or overdue
then make integration request, and save result to db)

`coin.` - to find allowed currencies by its name part

## Containerization

Here the `Dockerfile` in the project root for this MS
And `docker-compose.yml` to run full environment