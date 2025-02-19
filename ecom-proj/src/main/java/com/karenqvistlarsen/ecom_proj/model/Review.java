package com.karenqvistlarsen.ecom_proj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    private int id;
    private String name;
    private float rating;
    private String text;
}