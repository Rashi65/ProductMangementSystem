package com.product.Service;


import com.product.Entity.Product;
import com.product.Repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.listAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    public void addProduct(Product product) {
        productRepository.persist(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            // Save the updated product back to the repository
            productRepository.persist(existingProduct);
        }
        return existingProduct;
    }


    public boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return true;
    }

    public boolean checkStockAvailability(Long id, int count) {
        Product product = productRepository.findById(id);
        return product != null && product.getQuantity() >= count;
    }

    public List<Product> listAllSortedByPrice() {
        return productRepository.find("order by price").list();
    }
}
