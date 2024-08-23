package com.product.Controller;


import com.product.Entity.Product;
import com.product.Service.ProductService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public Response getAllProducts() {
        List<Product> products = productService.listAll();
        if (products.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No products found")
                    .build();
        }
        return Response.ok(products).build();
    }


    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build();
        }
        return Response.ok(product).build();
    }


    @POST
    @Transactional
    public Response createProduct(Product product) {
        productService.addProduct(product);
        return Response.status(Response.Status.CREATED)
                .entity(product) // Include the product in the response
                .build();
    }

   @PUT
   @Transactional
   @Path("/{id}")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response updateProduct(@PathParam("id") Long id, Product product) {
       // Check if the product ID is valid and provided
       if (product == null || id == null) {
           return Response.status(Response.Status.BAD_REQUEST)
                   .entity("Invalid product data or ID")
                   .build();
       }

       // Check if the product exists
       Product existingProduct = productService.findById(id);
       if (existingProduct == null) {
           return Response.status(Response.Status.NOT_FOUND)
                   .entity("Product with ID " + id + " not found")
                   .build();
       }

       // Perform the update
       Product updatedProduct = productService.updateProduct(id, product);
       if (updatedProduct == null) {
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity("Failed to update the product")
                   .build();
       }

       // Return the updated product
       return Response.status(Response.Status.OK)
               .entity(updatedProduct) // Include the updated product in the response
               .build();
   }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build(); // Return 404 if the product does not exist
        }

        productService.deleteProduct(id);
        return Response.status(Response.Status.OK)
                .entity("Product with ID " + id + " has been successfully deleted")
                .build(); // Return 200 OK with a message on successful deletion
    }


    @GET
    @Path("/{id}/check-stock")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkStockAvailability(@PathParam("id") Long id, @QueryParam("count") int count) {
        Product product = productService.findById(id);
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product with ID " + id + " not found")
                    .build(); // Return 404 if product is not found
        }

        boolean available = product.getQuantity() >= count; // Check if the requested count is available
        if (available) {
            return Response.ok().build(); // Return 200 OK if enough stock is available
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Not enough stock for product with ID " + id)
                    .build(); // Return 404 if not enough stock
        }
    }

    @GET
    @Path("/sorted-by-price")
    public Response getProductsSortedByPrice() {
        List<Product> products = productService.listAllSortedByPrice();
        if (products.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No products found")
                    .build();
        }
        return Response.ok(products).build();
    }

}
