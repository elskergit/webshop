package com.karenqvistlarsen.ecom_proj.controller;

import com.karenqvistlarsen.ecom_proj.model.ProductDTO;
import com.karenqvistlarsen.ecom_proj.service.ProductService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        Optional<ProductDTO> productDTOOptional = service.getProductById(id);
        if (productDTOOptional.isPresent()) {
            return ResponseEntity.ok(productDTOOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestPart ProductDTO productDTO,
                                              @RequestPart MultipartFile imageFile) {
        Optional<ProductDTO> productDTOOptional = service.addProduct(productDTO, imageFile);
        if (productDTOOptional.isPresent()) {
            return ResponseEntity.ok(productDTOOptional.get());
        }
        else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
        Optional<ProductDTO> productDTOOptional = service.getImageByProductId(id);

        return productDTOOptional
                .map(productDTO -> ResponseEntity.ok()
                        .contentType(MediaType.valueOf(productDTO.getImageType()))
                        .body(productDTO.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id,
                                                 @RequestPart ProductDTO productDTO,
                                                 @RequestPart(required = false) MultipartFile imageFile) {
        Optional<ProductDTO> productDTOOptional = service.updateProduct(id, imageFile);
        if (productDTOOptional.isPresent()) {
            return ResponseEntity.ok(productDTOOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable int id) {
        Optional<ProductDTO> productDTOOptional = service.deleteProduct(id);
        if (productDTOOptional.isPresent()) {
            return ResponseEntity.ok(productDTOOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
