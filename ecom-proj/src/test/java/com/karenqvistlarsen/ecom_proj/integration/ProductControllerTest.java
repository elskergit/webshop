package com.karenqvistlarsen.ecom_proj.integration;

import com.karenqvistlarsen.ecom_proj.model.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional // each test method will run in its own transaction which is then rolled back
class ProductControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void noProducts() {
        ResponseEntity<List<Product>> productResponse =
                restTemplate.exchange("/api/products",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        List<Product> products = productResponse.getBody();
        HttpStatusCode responseCode = productResponse.getStatusCode();

        assertEquals(HttpStatus.OK, responseCode);
        assertFalse(products.isEmpty());
    }

    @Test
    void shouldAddProductSuccessfully() {
        String productJson = """
        {
            "name": "Laptop",
            "description": "Gaming Laptop",
            "brand": "Dell",
            "price": 1200.00,
            "category": "Electronics",
            "releaseDate": "2024-01-01",
            "productAvailable": true,
            "stockQuantity": 10
        }
        """;

        // headers and entity for JSON request part
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(productJson, jsonHeaders);

        // headers for multipart request
        HttpHeaders multipartHeaders = new HttpHeaders();
        multipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        // create multipart request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("product", jsonEntity);
        body.add("imageFile", new FileSystemResource("src/test/resources/laptop.jpg")); // Image file part

        // send request and receive response
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, multipartHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/product", HttpMethod.POST, requestEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void shouldAddAndFindProductSuccessfully() {
        String productJson = """
        {
            "name": "Laptop",
            "description": "Gaming Laptop",
            "brand": "Dell",
            "price": 1200.00,
            "category": "Electronics",
            "releaseDate": "2024-01-01",
            "productAvailable": true,
            "stockQuantity": 10
        }
        """;

        // headers and entity for JSON request part
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(productJson, jsonHeaders);

        // headers for multipart request
        HttpHeaders multipartHeaders = new HttpHeaders();
        multipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        // create multipart request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("product", jsonEntity);
        body.add("imageFile", new FileSystemResource("src/test/resources/laptop.jpg")); // Image file part

        // save product
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, multipartHeaders);
        restTemplate.exchange("/api/product", HttpMethod.POST, requestEntity, String.class);

        // request saved product
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/product/1", Product.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("Laptop", response.getBody().getName());
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() {
        // Assuming product with ID 999 does not exist
        ResponseEntity<Product> response = restTemplate.getForEntity("/api/product/999", Product.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldAddAndDeleteProductSuccessfully() {
        String productJson = """
        {
            "name": "Laptop",
            "description": "Gaming Laptop",
            "brand": "Dell",
            "price": 1200.00,
            "category": "Electronics",
            "releaseDate": "2024-01-01",
            "productAvailable": true,
            "stockQuantity": 10
        }
        """;

        // headers and entity for JSON request part
        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonEntity = new HttpEntity<>(productJson, jsonHeaders);

        // headers for multipart request
        HttpHeaders multipartHeaders = new HttpHeaders();
        multipartHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        // create multipart request body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("product", jsonEntity);
        body.add("imageFile", new FileSystemResource("src/test/resources/laptop.jpg")); // Image file part

        // save product
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, multipartHeaders);
        restTemplate.exchange("/api/product", HttpMethod.POST, requestEntity, String.class);

        // request saved product
        ResponseEntity<Product> responseProductExists = restTemplate.getForEntity("/api/product/1", Product.class);

        // delete product
        ResponseEntity<String> responseProductDelete = restTemplate.exchange(
                "/api/product/1", HttpMethod.DELETE, null, String.class);

        // assert product added successfully
        assertEquals(HttpStatus.OK, responseProductExists.getStatusCode());
        assertNotNull(responseProductExists.getBody());
        assertEquals(1, responseProductExists.getBody().getId());
        assertEquals("Laptop", responseProductExists.getBody().getName());

        // assert product deleted successfully
        assertEquals(HttpStatus.OK, responseProductDelete.getStatusCode());
        assertEquals("Deleted", responseProductDelete.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenProductToBeDeletedDoesNotExist() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/product/999", HttpMethod.DELETE, null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found", response.getBody());
    }


}
