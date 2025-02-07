package com.karenqvistlarsen.ecom_proj.unit;

import com.karenqvistlarsen.ecom_proj.model.Product;
import com.karenqvistlarsen.ecom_proj.repository.ProductRepository;
import com.karenqvistlarsen.ecom_proj.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private MultipartFile imageFile;
    private Product product;

    @BeforeEach
    void setup() throws IOException {
        Date date = new Date();
        product = new Product(1, "Laptop", "Gaming Laptop", "Dell",
                new BigDecimal("1200.00"), "Electronics", date,
                true, 10, "image.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        // mock image behavior
        byte[] imageData = new byte[]{1, 2, 3, 4};
        String fileName = "image.jpg";
        String contentType = "image/jpeg";

        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);
    }


    @Test
    void shouldAddProductWithImage() {
        // assert product is correctly saved and returned
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Optional<Product> addedProduct = productService.addProduct(product, imageFile);

        assertThat(addedProduct)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        Product resultProduct = addedProduct.get();

        assertThat(resultProduct.getId()).isEqualTo(1);
        assertThat(resultProduct.getName()).isEqualTo("Laptop");
        assertThat(resultProduct.getDescription()).isEqualTo("Gaming Laptop");
        assertThat(resultProduct.getBrand()).isEqualTo("Dell");
        assertThat(resultProduct.getPrice()).isEqualByComparingTo(new BigDecimal("1200.00"));
        assertThat(resultProduct.getCategory()).isEqualTo("Electronics");
        assertThat(resultProduct.isProductAvailable()).isTrue();
        assertThat(resultProduct.getStockQuantity()).isEqualTo(10);
        assertThat(resultProduct.getImageName()).isEqualTo("image.jpg");
        assertThat(resultProduct.getImageType()).isEqualTo("image/jpeg");
        assertThat(resultProduct.getImageData()).isEqualTo(new byte[]{1, 2, 3, 4});
    }


    @Test
    void shouldAddAndUpdateProductWithImage() {
        // mock repo findById method to return existing product
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // assert product is correctly saved, updated and returned
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<Product> updatedProduct = productService.updateProduct(product.getId(), imageFile);

        assertThat(updatedProduct)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        Product resultProduct = updatedProduct.get();

        assertThat(resultProduct.getId()).isEqualTo(1);
        assertThat(resultProduct.getName()).isEqualTo("Laptop");
        assertThat(resultProduct.getDescription()).isEqualTo("Gaming Laptop");
        assertThat(resultProduct.getBrand()).isEqualTo("Dell");
        assertThat(resultProduct.getPrice()).isEqualByComparingTo(new BigDecimal("1200.00"));
        assertThat(resultProduct.getCategory()).isEqualTo("Electronics");
        assertThat(resultProduct.isProductAvailable()).isTrue();
        assertThat(resultProduct.getStockQuantity()).isEqualTo(10);
        assertThat(resultProduct.getImageName()).isEqualTo("image.jpg");
        assertThat(resultProduct.getImageType()).isEqualTo("image/jpeg");
        assertThat(resultProduct.getImageData()).isEqualTo(new byte[]{1, 2, 3, 4});
    }

    @Test
    void shouldAddAndDeleteProduct() {
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Optional<Product> addedProduct = productService.addProduct(product, imageFile);

        assertThat(addedProduct)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1);

        Optional<Product> deletedProduct = productService.deleteProduct(1);

        assertThat(deletedProduct)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        Product resultDeletedProduct = deletedProduct.get();
        Product resultAddedProduct = deletedProduct.get();

        // assert that deleted product is equal to added product
        assertThat(resultDeletedProduct.getId()).isEqualTo(resultAddedProduct.getId());
        assertThat(resultDeletedProduct.getName()).isEqualTo(resultAddedProduct.getName());
        assertThat(resultDeletedProduct.getDescription()).isEqualTo(resultAddedProduct.getDescription());
        assertThat(resultDeletedProduct.getBrand()).isEqualTo(resultAddedProduct.getBrand());
        assertThat(resultDeletedProduct.getPrice()).isEqualTo(resultAddedProduct.getPrice());
        assertThat(resultDeletedProduct.getCategory()).isEqualTo(resultAddedProduct.getCategory());
        assertThat(resultDeletedProduct.isProductAvailable()).isEqualTo(resultAddedProduct.isProductAvailable());
        assertThat(resultDeletedProduct.getStockQuantity()).isEqualTo(resultAddedProduct.getStockQuantity());
        assertThat(resultDeletedProduct.getImageName()).isEqualTo(resultAddedProduct.getImageName());
        assertThat(resultDeletedProduct.getImageType()).isEqualTo(resultAddedProduct.getImageType());
        assertThat(resultDeletedProduct.getImageData()).isEqualTo(resultAddedProduct.getImageData());
    }
}

