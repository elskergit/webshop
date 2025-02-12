package com.karenqvistlarsen.ecom_proj.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDate requestPosted;
}
