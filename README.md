# **_Product Management API_**

# Overview

**The Product Management API is a RESTful service built with Quarkus. It allows you to manage products 
by performing CRUD operations and checking stock availability. This API includes endpoints for retrieving 
all products, getting a specific product by ID, updating, deleting products, and checking stock availability.**

# Getting Started

# Prerequisites

Java 21 or higher
Maven 3.8.6 or higher
Quarkus as the framework
JPA for persistence
RESTEasy
H2 in-memory

# API Curls

1. ADD PRODUCT

curl --location 'http://localhost:8080/products' \
--header 'Content-Type: application/json' \
--data '{
"name": "New Product2",
"description": "Description of the new product",
"price": 5.99,
"quantity": 200
}'

2. GET BY ID 

curl --location 'http://localhost:8080/products/1' \
--header 'Accept: application/json'

3. SORT BY PRICE

curl --location 'http://localhost:8080/products/sorted-by-price' \
--header 'Accept: application/json'

4. STOCK COUNT

curl --location 'http://localhost:8080/products/1/check-stock?count=120' \
--header 'Accept: application/json'

5. DELETE PRODUCT

curl --location --request DELETE 'http://localhost:8080/products/1'

6. UPDATE BY ID

curl --location --request PUT 'http://localhost:8080/products/2' \
--header 'Content-Type: application/json' \
--data '{
"name": "Updated Product Nam1",
"description": "Updated description",
"price": 29.99,
"quantity": 100
}'

7. GET ALL PRODUCTS

curl --location 'http://localhost:8080/products'

# Steps For Running Application

1. Clone the Application from git.
2. Import it in Intellij.
3. Maven clean from right side of IDE.
4. Run the application with below command :
   ./mvnw quarkus:dev
5. Run above curls and get the response.
6. For running testcases, run the play button in ProductResourceTest class.

