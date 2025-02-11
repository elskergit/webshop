package com.karenqvistlarsen.ecom_proj.controller;

import com.karenqvistlarsen.ecom_proj.model.ProductDTO;
import com.karenqvistlarsen.ecom_proj.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
        return service.getProductById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestPart ProductDTO productDTO,
                                              @RequestPart MultipartFile imageFile) {
        return service.addProduct(productDTO, imageFile).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
        return service.getProductById(id)
                .map(productDTO -> ResponseEntity.ok()
                        .contentType(MediaType.valueOf(productDTO.getImageType()))
                        .body(productDTO.getImageData()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable int id,
                                                 @RequestPart ProductDTO productDTO,
                                                 @RequestPart(required = false) MultipartFile imageFile) {
        return service.updateProduct(id, imageFile).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable int id) {
        return service.deleteProduct(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
