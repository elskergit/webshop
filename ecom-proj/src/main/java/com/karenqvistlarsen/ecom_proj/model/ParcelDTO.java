package com.karenqvistlarsen.ecom_proj.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDTO {
    private int id;
    private String dest;
    private String source;
    private LocalDate pickUpDate;
    private String serviceTier;
    private Double width;
    private Double height;
    private Double weight;
    private String customerName;
    private Double price;
    private LocalDate deliveryDate;
}
