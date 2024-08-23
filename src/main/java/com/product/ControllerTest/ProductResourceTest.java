package com.product.ControllerTest;

import com.product.Entity.Product;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductResourceTest {

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("New Product");
        product.setDescription("A new product description");
        product.setPrice(99.99);
        product.setQuantity(10);

        given()
                .contentType("application/json")
                .body(product)
                .when().post("/products")
                .then()
                .statusCode(201)
                .body("name", equalTo("New Product"))
                .body("description", equalTo("A new product description"))
                .body("price", equalTo(99.99f))
                .body("quantity", equalTo(10));
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L; // Use a valid product ID
        given()
                .pathParam("id", productId)
                .when().get("/products/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(productId.intValue()));
    }

    @Test
    public void testGetProductByIdNotFound() {
        Long invalidProductId = 999L; // Use an invalid product ID
        given()
                .pathParam("id", invalidProductId)
                .when().get("/products/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L; // Use a valid product ID
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated description");
        updatedProduct.setPrice(199.99);
        updatedProduct.setQuantity(5);

        given()
                .pathParam("id", productId)
                .contentType("application/json")
                .body(updatedProduct)
                .when().put("/products/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Product"))
                .body("description", equalTo("Updated description"))
                .body("price", equalTo(199.99f))
                .body("quantity", equalTo(5));
    }


    @Test
    public void testUpdateProductNotFound() {
        Long invalidProductId = 999L; // Use an invalid product ID
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated description");
        updatedProduct.setPrice(199.99);
        updatedProduct.setQuantity(5);

        given()
                .pathParam("id", invalidProductId)
                .contentType("application/json")
                .body(updatedProduct)
                .when().put("/products/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L; // Use a valid product ID
        given()
                .pathParam("id", productId)
                .when().delete("/products/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteProductNotFound() {
        Long invalidProductId = 999L; // Use an invalid product ID
        given()
                .pathParam("id", invalidProductId)
                .when().delete("/products/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testCheckStockAvailability() {
        Long productId = 1L; // Use a valid product ID
        int count = 5; // Ensure this is less than or equal to the product's quantity

        given()
                .pathParam("id", productId)
                .queryParam("count", count)
                .when().get("/products/{id}/check-stock")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCheckStockAvailabilityInsufficientStock() {
        Long productId = 1L; // Use a valid product ID
        int count = 999; // Ensure this is greater than the product's quantity

        given()
                .pathParam("id", productId)
                .queryParam("count", count)
                .when().get("/products/{id}/check-stock")
                .then()
                .statusCode(404);
    }

    @Test
    public void testCheckStockAvailabilityProductNotFound() {
        Long invalidProductId = 999L; // Use an invalid product ID
        int count = 1;

        given()
                .pathParam("id", invalidProductId)
                .queryParam("count", count)
                .when().get("/products/{id}/check-stock")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetProductsSortedByPrice() {
        given()
                .when().get("/products/sorted-by-price")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0)) // Ensure there is at least one product
                .body("[0].price", lessThanOrEqualTo(
                        given().when().get("/products/sorted-by-price").then().extract().jsonPath().getList("$[1].price", Float.class).stream().findFirst().orElse(Float.MAX_VALUE)
                ));
    }

}

