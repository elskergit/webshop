package com.karenqvistlarsen.ecom_proj.service;

import com.karenqvistlarsen.ecom_proj.exception.FileProcessingException;
import com.karenqvistlarsen.ecom_proj.exception.ProductNotFoundException;
import com.karenqvistlarsen.ecom_proj.model.Product;
import com.karenqvistlarsen.ecom_proj.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return Optional.ofNullable(repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found")));
    }

    public Optional<Product> addProduct(Product product, MultipartFile imageFile) {
        try {
            // Set product's image properties
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());

            // Save and return the product wrapped in Optional
            repo.save(product);
            return Optional.of(product);
        } catch (IOException e) {
            // Handle the IOException by throwing a custom exception
            throw new FileProcessingException("File processing error: " + e.getMessage(), e);
        }
    }

    public Optional<Product> updateProduct(int id, MultipartFile imageFile) {
        try {
            Product product = getProductById(id).orElseThrow(() ->
                    new ProductNotFoundException("Product with ID " + id + " not found"));

            product.setImageData(imageFile.getBytes());
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());

            return Optional.of(repo.save(product));
        } catch (IOException e) {
            throw new FileProcessingException("File processing error: " + e.getMessage(), e);
        }
    }

    public Optional<Product> deleteProduct(int id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));

        repo.deleteById(id);

        return Optional.of(product);
    }
}
