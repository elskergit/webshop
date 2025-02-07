package com.karenqvistlarsen.ecom_proj.controller;

import com.karenqvistlarsen.ecom_proj.exception.ProductNotFoundException;
import com.karenqvistlarsen.ecom_proj.model.Product;
import com.karenqvistlarsen.ecom_proj.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        try {
            return service.getProductById(id)
                    .map(product1 -> ResponseEntity.ok(product1))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product,
                                              @RequestPart MultipartFile imageFile) {
        Optional<Product> productOptional = service.addProduct(product, imageFile);
        if (productOptional.isPresent()) {
            return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
        return service.getProductById(id)
                .map(product -> ResponseEntity.ok()
                        .contentType(MediaType.valueOf(product.getImageType()))
                        .body(product.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id,
                                                 @RequestPart Product product,
                                                 @RequestPart(required = false) MultipartFile imageFile) {
        Optional<Product> productOptional = service.updateProduct(id, imageFile);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        } else {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        Optional<Product> productOptional = service.deleteProduct(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        } else {
            throw new ProductNotFoundException("Product with ID " + id + " could not be deleted as it was not found");
        }
    }
}
