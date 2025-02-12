package com.karenqvistlarsen.ecom_proj.integration;

import com.karenqvistlarsen.ecom_proj.model.Parcel;
import com.karenqvistlarsen.ecom_proj.repository.ParcelRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParcelControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ParcelRepository parcelRepository;
    private HttpEntity<String> requestEntity;

    @BeforeEach
    void setup() {
        parcelRepository.deleteAll();
        String parcelDTOJson = """
        {
          "dest": "New York",
          "source": "Los Angeles",
          "pickUpTime": "2025-02-11T10:00:00Z",
          "serviceTier": "express",
          "width": 10.5,
          "height": 5,
          "weight": 2.5,
          "customerName": "John Doe"
        }
        """;

        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestEntity = new HttpEntity<>(parcelDTOJson, jsonHeaders);
    }

    @Test
    @Order(1)
    void noParcels() {
        ResponseEntity<List<Parcel>> parcelResponse =
                restTemplate.exchange("/api/parcels",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        List<Parcel> parcels = parcelResponse.getBody();
        HttpStatusCode responseCode = parcelResponse.getStatusCode();

        assertEquals(HttpStatus.OK, responseCode);
        assertTrue(parcels.isEmpty());
    }

    @Test
    @Order(2)
    void shouldReturnNotFoundWhenParcelDoesNotExist() {
        ResponseEntity<Parcel> response = restTemplate.getForEntity("/api/parcel/999", Parcel.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    void shouldReturnNotFoundWhenParcelToBeDeletedDoesNotExist() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/parcel/999", HttpMethod.DELETE, null, String.class);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(4)
    void shouldAddParcelSuccessfully() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/parcel", HttpMethod.POST, requestEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    void shouldAddAndFindParcelSuccessfully() {
        restTemplate.exchange("/api/parcel", HttpMethod.POST, requestEntity, String.class);

        ResponseEntity<Parcel> response = restTemplate.getForEntity("/api/parcel/1", Parcel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    @Order(6)
    void shouldAddAndDeleteParcelSuccessfully() {
        restTemplate.exchange("/api/parcel", HttpMethod.POST, requestEntity, String.class);

        ResponseEntity<Parcel> responseParcelExists = restTemplate.getForEntity("/api/parcel/1", Parcel.class);

        ResponseEntity<String> responseParcelDelete = restTemplate.exchange(
                "/api/parcel/1", HttpMethod.DELETE, null, String.class);

        ResponseEntity<List<Parcel>> parcelsResponse =
                restTemplate.exchange("/api/parcels",
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        assertEquals(HttpStatus.OK, responseParcelExists.getStatusCode());
        assertNotNull(responseParcelExists.getBody());
        assertEquals(1, responseParcelExists.getBody().getId());

        assertEquals(HttpStatus.OK, responseParcelDelete.getStatusCode());
        assertNotNull(responseParcelDelete);
        assertEquals(Collections.emptyList(), parcelsResponse.getBody());
    }
}
