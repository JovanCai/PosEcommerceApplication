# POS integrated e-commerce platform: Point System

----

## Introduction

This is a POS integrated e-commerce platform which offers a point system and has a vast collection of payment methods
integrated.

Which offers：

- Calculate the final price and points of the order according to the price, price modifier, and payment method
- Get how much sales were made within a date range broken down into hours

## Installation

1. Clone the repository
2. Run the `docker-compose up -d` command under the "resources" folder to start the database  
   (If you don't have docker, please install it first https://docs.docker.com/get-docker/)
3. `./gradlew bootRun` to start the application

## Example queries

You can use the following curl commands to test the application of making a payment.

```curl
curl --location 'localhost:8080/api/transaction' \
--header 'Content-Type: application/json' \
--data '{
"price":"100.00",
"priceModifier": 0.95,
"paymentMethod": "MASTERCARD",
"dateTime": "2022-09-01T00:19:00Z"
}'
```

Or you can use Postman
![image](https://user-images.githubusercontent.com/124067692/219943544-2a41b4f4-833e-46d9-812f-c4aad32dded8.png)

----

Test the application of getting total price and points.

```curl
curl --location --request GET 'localhost:8080/api/sales' \
--header 'Content-Type: application/json' \
--data '{
"startDateTime":
"2022-09-01T00:19:00Z",
"endDateTime":
"2022-09-02T23:59:59Z"
}'
``` 
![image](https://user-images.githubusercontent.com/124067692/219943636-284288fc-37d1-4b22-ac33-88369265490c.png)
