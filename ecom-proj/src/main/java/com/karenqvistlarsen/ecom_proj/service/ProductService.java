package com.karenqvistlarsen.ecom_proj.service;

import com.karenqvistlarsen.ecom_proj.exception.FileProcessingException;
import com.karenqvistlarsen.ecom_proj.exception.ProductNotFoundException;
import com.karenqvistlarsen.ecom_proj.model.Product;
import com.karenqvistlarsen.ecom_proj.model.ProductDTO;
import com.karenqvistlarsen.ecom_proj.model.ProductDTOMapper;
import com.karenqvistlarsen.ecom_proj.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ProductDTOMapper productDTOMapper;

    public List<ProductDTO> getAllProducts() {
        return repo.findAll()
                .stream()
                .map(productDTOMapper::productToProductDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(int id) {
        return Optional.ofNullable(repo.findById(id)
                .map(productDTOMapper::productToProductDTO)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [%s] not found".formatted(id))));
    }

    public Optional<ProductDTO> addProduct(ProductDTO productDTO, MultipartFile imageFile) {
        try {
            productDTO.setImageName(imageFile.getOriginalFilename());
            productDTO.setImageType(imageFile.getContentType());
            productDTO.setImageData(imageFile.getBytes());

            // convert and save entity
            Product product = productDTOMapper.productDTOToProduct(productDTO);
            Product savedProduct = repo.save(product);

            // convert back to DTO and return
            ProductDTO savedProductDTO = productDTOMapper.productToProductDTO(savedProduct);

            return Optional.of(savedProductDTO);
        } catch (IOException e) {
            throw new FileProcessingException("File processing error: " + e.getMessage(), e);
        }
    }

    public Optional<ProductDTO> updateProduct(int id, MultipartFile imageFile) {
        try {
            ProductDTO existingProductDTO = getProductById(id).orElseThrow(
                    () -> new ProductNotFoundException("Product with id [%s] not found".formatted(id))
            );

            existingProductDTO.setImageName(imageFile.getOriginalFilename());
            existingProductDTO.setImageType(imageFile.getContentType());
            existingProductDTO.setImageData(imageFile.getBytes());

            // convert DTO to entity and save updated product
            Product updatedProduct = productDTOMapper.productDTOToProduct(existingProductDTO);
            Product savedProduct = repo.save(updatedProduct);

            // convert back to DTO and return
            return Optional.of(productDTOMapper.productToProductDTO(savedProduct));
        } catch (IOException e) {
            throw new FileProcessingException("File processing error: " + e.getMessage(), e);
        }
    }

    public Optional<ProductDTO> deleteProduct(int id) {
        ProductDTO existingProductDTO = getProductById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id [%s] not found".formatted(id))
        );

        repo.deleteById(id);
        return Optional.of(existingProductDTO);
    }

    public Optional<ProductDTO> getImageByProductId(int id) {
        ProductDTO productDTO = getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [%s] not found".formatted(id)));

        return Optional.ofNullable(productDTO);
    }
}
