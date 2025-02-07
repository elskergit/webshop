package com.karenqvistlarsen.ecom_proj.unit;

import com.karenqvistlarsen.ecom_proj.model.Product;
import com.karenqvistlarsen.ecom_proj.repository.ProductRepository;
import com.karenqvistlarsen.ecom_proj.service.ProductService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private MultipartFile imageFile;

    @Test
    void shouldAddProductWithImage() throws IOException {
        // Arrange: Prepare a product and mock repository behavior
        Date date = new Date();
        Product product = new Product(1, "Laptop", "Gaming Laptop", "Dell",
                new BigDecimal("1200.00"), "Electronics", date,
                true, 10, "image.jpg", "image/jpeg", new byte[]{});

        // mock image
        byte[] imageData = new byte[]{1, 2, 3, 4};
        String fileName = "newImage.jpg";
        String contentType = "image/png";

        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);
        when(productRepository.save(product)).thenReturn(product);

        Product addedProduct = productService.addProduct(product, imageFile);

        assertThat(addedProduct).isNotNull();
        assertThat(addedProduct.getName()).isEqualTo("Laptop");
        assertThat(addedProduct.getId()).isEqualTo(1);
        assertThat(addedProduct.getImageName()).isEqualTo(fileName);
        assertThat(addedProduct.getImageType()).isEqualTo(contentType);
        assertThat(addedProduct.getImageData()).isEqualTo(imageData);
    }

    @Test
    void shouldUpdateProductWithImage() throws IOException {
        // Arrange
        Date date = new Date();
        Product product = new Product(1, "Laptop", "Gaming Laptop", "Dell",
                new BigDecimal("1200.00"), "Electronics", date,
                true, 10, "image.jpg", "image/jpeg", new byte[]{});

        // Mocking the MultipartFile to return some image data
        byte[] imageData = new byte[]{1, 2, 3, 4};  // Sample byte array for image
        String fileName = "newImage.jpg";
        String contentType = "image/png";

        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);

        when(productRepository.save(product)).thenReturn(product);
        Product updatedProduct = productService.updateProduct(product, imageFile);

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getImageData()).isEqualTo(imageData);
        assertThat(updatedProduct.getImageName()).isEqualTo(fileName);
        assertThat(updatedProduct.getImageType()).isEqualTo(contentType);
    }

    @Test
    void shouldAddAndDeleteProduct() throws IOException {
        // Arrange: Prepare a product and mock repository behavior
        Date date = new Date();
        Product product = new Product(1, "Laptop", "Gaming Laptop", "Dell",
                new BigDecimal("1200.00"), "Electronics", date,
                true, 10, "image.jpg", "image/jpeg", new byte[]{});

        // mock image
        byte[] imageData = new byte[]{1, 2, 3, 4};
        String fileName = "newImage.jpg";
        String contentType = "image/png";

        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);
        when(productRepository.save(product)).thenReturn(product);

        // add product and assert
        Product addedProduct = productService.addProduct(product, imageFile);

        assertThat(addedProduct).isNotNull();
        assertThat(addedProduct.getName()).isEqualTo("Laptop");
        assertThat(addedProduct.getId()).isEqualTo(1);

        // delete product and assert
        productService.deleteProduct(1);
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Product deletedProduct = productService.getProductById(1);
        assertThat(deletedProduct).isNull();
    }
}

