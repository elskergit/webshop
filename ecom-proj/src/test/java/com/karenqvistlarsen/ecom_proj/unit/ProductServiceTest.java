package com.karenqvistlarsen.ecom_proj.unit;

import com.karenqvistlarsen.ecom_proj.model.Product;
import com.karenqvistlarsen.ecom_proj.model.ProductDTO;
import com.karenqvistlarsen.ecom_proj.model.ProductDTOMapper;
import com.karenqvistlarsen.ecom_proj.repository.ProductRepository;
import com.karenqvistlarsen.ecom_proj.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    @Spy
    private ProductDTOMapper productDTOMapper = Mappers.getMapper(ProductDTOMapper.class);
    private Product product;
    private ProductDTO productDTO;
    private byte[] imageData;
    private String fileName;
    private String contentType;

    @BeforeEach
    void setup() {
        Date date = new Date();
        product = new Product(1, "Laptop", "Gaming Laptop", "Dell",
                new BigDecimal("1200.00"), "Electronics", date,
                true, 10, "image.jpg", "image/jpeg", new byte[]{1, 2, 3, 4},
                "Worker1");

        productDTO = new ProductDTO(1, "Laptop", "Gaming Laptop", "Dell",
                new BigDecimal("1200.00"), "Electronics", new Date(),
                true, 10, "image.jpg", "image/jpeg", new byte[]{1, 2, 3, 4});

        imageData = new byte[]{1, 2, 3, 4};
        fileName = "image.jpg";
        contentType = "image/jpeg";
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> productList = List.of(product);
        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDTO> result = productService.getAllProducts();

        assertThat(result)
                .withFailMessage("Expected a list with one product, but got a different size")
                .hasSize(1);

        ProductDTO resultProductDTO = result.get(0);

        assertThat(resultProductDTO.getId()).isEqualTo(product.getId());
        assertThat(resultProductDTO.getName()).isEqualTo(product.getName());
        assertThat(resultProductDTO.getDescription()).isEqualTo(product.getDescription());
        assertThat(resultProductDTO.getBrand()).isEqualTo(product.getBrand());
        assertThat(resultProductDTO.getPrice()).isEqualByComparingTo(product.getPrice());
        assertThat(resultProductDTO.getCategory()).isEqualTo(product.getCategory());
        assertThat(resultProductDTO.isProductAvailable()).isEqualTo(product.isProductAvailable());
        assertThat(resultProductDTO.getCount()).isEqualTo(product.getStockQuantity());
        assertThat(resultProductDTO.getImageName()).isEqualTo(product.getImageName());
        assertThat(resultProductDTO.getImageType()).isEqualTo(product.getImageType());
        assertThat(resultProductDTO.getImageData()).isEqualTo(product.getImageData());
    }

    @Test
    void shouldAddProductWithImage() throws IOException {
        // mock image behavior
        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);

        // assert product is correctly saved and returned
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(productDTOMapper.productToProductDTO(any(Product.class))).thenReturn(productDTO);

        Optional<ProductDTO> addedProductDTO = productService.addProduct(productDTO, imageFile);

        assertThat(addedProductDTO)
                .withFailMessage("Expected ProductDTO to be present, but it was empty")
                .isPresent();

        ProductDTO resultProductDTO = addedProductDTO.get();

        assertThat(resultProductDTO.getId()).isEqualTo(1);
        assertThat(resultProductDTO.getName()).isEqualTo("Laptop");
        assertThat(resultProductDTO.getDescription()).isEqualTo("Gaming Laptop");
        assertThat(resultProductDTO.getBrand()).isEqualTo("Dell");
        assertThat(resultProductDTO.getPrice()).isEqualByComparingTo(new BigDecimal("1200.00"));
        assertThat(resultProductDTO.getCategory()).isEqualTo("Electronics");
        assertThat(resultProductDTO.isProductAvailable()).isTrue();
        assertThat(resultProductDTO.getCount()).isEqualTo(10);
        assertThat(resultProductDTO.getImageName()).isEqualTo("image.jpg");
        assertThat(resultProductDTO.getImageType()).isEqualTo("image/jpeg");
        assertThat(resultProductDTO.getImageData()).isEqualTo(new byte[]{1, 2, 3, 4});
    }


    @Test
    void shouldAddAndUpdateProductWithImage() throws IOException {
        // mock image behavior
        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);

        // mock repo findById method to return existing product
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // assert product is correctly saved, updated and returned
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Optional<ProductDTO> updatedProductDTO = productService.updateProduct(product.getId(), imageFile);

        assertThat(updatedProductDTO)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        ProductDTO resultProductDTO = updatedProductDTO.get();

        assertThat(resultProductDTO.getId()).isEqualTo(1);
        assertThat(resultProductDTO.getName()).isEqualTo("Laptop");
        assertThat(resultProductDTO.getDescription()).isEqualTo("Gaming Laptop");
        assertThat(resultProductDTO.getBrand()).isEqualTo("Dell");
        assertThat(resultProductDTO.getPrice()).isEqualByComparingTo(new BigDecimal("1200.00"));
        assertThat(resultProductDTO.getCategory()).isEqualTo("Electronics");
        assertThat(resultProductDTO.isProductAvailable()).isTrue();
        assertThat(resultProductDTO.getCount()).isEqualTo(10);
        assertThat(resultProductDTO.getImageName()).isEqualTo("image.jpg");
        assertThat(resultProductDTO.getImageType()).isEqualTo("image/jpeg");
        assertThat(resultProductDTO.getImageData()).isEqualTo(new byte[]{1, 2, 3, 4});
    }

    @Test
    void shouldAddAndDeleteProduct() throws IOException {
        // mock image behavior
        when(imageFile.getBytes()).thenReturn(imageData);
        when(imageFile.getOriginalFilename()).thenReturn(fileName);
        when(imageFile.getContentType()).thenReturn(contentType);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Optional<ProductDTO> addedProductDTO = productService.addProduct(productDTO, imageFile);

        assertThat(addedProductDTO)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1);

        Optional<ProductDTO> deletedProductDTO = productService.deleteProduct(1);

        assertThat(deletedProductDTO)
                .withFailMessage("Expected product to be present, but it was empty")
                .isPresent();

        ProductDTO resultDeletedProductDTO = deletedProductDTO.get();
        ProductDTO resultAddedProductDTO = addedProductDTO.get();

        // assert that deleted product is equal to added product
        assertThat(resultDeletedProductDTO.getId()).isEqualTo(resultAddedProductDTO.getId());
        assertThat(resultDeletedProductDTO.getName()).isEqualTo(resultAddedProductDTO.getName());
        assertThat(resultDeletedProductDTO.getDescription()).isEqualTo(resultAddedProductDTO.getDescription());
        assertThat(resultDeletedProductDTO.getBrand()).isEqualTo(resultAddedProductDTO.getBrand());
        assertThat(resultDeletedProductDTO.getPrice()).isEqualTo(resultAddedProductDTO.getPrice());
        assertThat(resultDeletedProductDTO.getCategory()).isEqualTo(resultAddedProductDTO.getCategory());
        assertThat(resultDeletedProductDTO.isProductAvailable()).isEqualTo(resultAddedProductDTO.isProductAvailable());
        assertThat(resultDeletedProductDTO.getCount()).isEqualTo(resultAddedProductDTO.getCount());
        assertThat(resultDeletedProductDTO.getImageName()).isEqualTo(resultAddedProductDTO.getImageName());
        assertThat(resultDeletedProductDTO.getImageType()).isEqualTo(resultAddedProductDTO.getImageType());
        assertThat(resultDeletedProductDTO.getImageData()).isEqualTo(resultAddedProductDTO.getImageData());
    }
}

