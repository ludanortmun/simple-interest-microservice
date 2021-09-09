# Simple Interest Microservice

This is a microservice that calculates and generates a list of weekly payments for a given credit. Built with Spring
Boot.

Created by Daniel Ortega.

## Main Features

- Can persist all the credits and their payments (using an H2 database)
- Dockerized
- Code Coverage: 100% (classes); 93.3% (methods); 95.5% (lines). See [reports/index.html](reports/index.html)
- Health endpoint (using the Actuator
  module) [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

## How to use

1. Build the application with Gradle: `gradlew build`
2. Create the Docker image: `docker build -t simple-interest-microservice .`
3. Start the image: `docker run -it -p 8080:8080 simple-interest-microservice`
4. The server will start listening in port `8080`

## Endpoints

### Health check (GET)

- URL: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- Response Example:

```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "H2",
        "validationQuery": "isValid()"
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 269490393088,
        "free": 243053350912,
        "threshold": 10485760,
        "exists": true
      }
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

### Credits (POST)

Creates a `Credit` entry in the database and computes the list of weekly payments for it.

- URL: [http://localhost:8080/credits](http://localhost:8080/credits)
- Request Example:

```json
{
  "amount": 10,
  "terms": 2,
  "rate": 0.2
}
```

- Response Example:

```json
{
  "amount": 10.0,
  "terms": 2,
  "rate": 0.2,
  "payments": [
    {
      "payment_number": 1,
      "amount": 6.0,
      "payment_date": "2021-09-09"
    },
    {
      "payment_number": 2,
      "amount": 6.0,
      "payment_date": "2021-09-16"
    }
  ],
  "credit_id": "d189e8bf-9a1f-4f96-90a1-9977d5e969f5"
}
```

### Credit (GET)

Reads a `Credit` entry based on the ID and returns the payments for it.

- URL: [http://localhost:8080/credits/{id}](http://localhost:8080/credits/{ID})
- Response Example:

```json
{
  "amount": 10.0,
  "terms": 2,
  "rate": 0.2,
  "payments": [
    {
      "payment_number": 1,
      "amount": 6.0,
      "payment_date": "2021-09-09"
    },
    {
      "payment_number": 2,
      "amount": 6.0,
      "payment_date": "2021-09-16"
    }
  ],
  "credit_id": "d189e8bf-9a1f-4f96-90a1-9977d5e969f5"
}
```

## Additional Remarks

Even though the original requirement was to only return the payments array as the response, I decided to return the
whole representation of the `Credit` object. This was done due to the following considerations:

- Having the `credit_id` in the `[POST] /credits` response is necessary if we want to read that same entry afterwards.
- Retrieving the credit by its ID should return the whole object representation.
- The representation should be consistent across all endpoints that allow the user to interact with that resource.

Another decision was to not make use of an ORM, and instead perform the database operations by manually creating the SQL
queries and parsing the result sets. While this may prove to be harder to maintain in a larger project, for the purposes
and the scope of this particular implementation I believe it doesn't have a negative impact.

Last but not least, these are the main areas of improvement that were overlooked due to the fact of this being a simple
exercise, but that should not be omitted on any real-life application:

- Using an SSL connection
- Implementing authentication and authorization
- Add server logging (for domain events, performance metrics, diagnostic information, etc)
- Database schema improvements (mainly PK/FK constraints and column indexing)