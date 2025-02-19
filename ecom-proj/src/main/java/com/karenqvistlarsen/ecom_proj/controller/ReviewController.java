package com.karenqvistlarsen.ecom_proj.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karenqvistlarsen.ecom_proj.model.Review;
import com.karenqvistlarsen.ecom_proj.service.ReviewService;

import lombok.AllArgsConstructor;


@RestController
@CrossOrigin
@RequestMapping("/api")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @GetMapping("/reviews")
    public List<Review> getReviews() {
        return this.service.getReviews();
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review newReview = this.service.addReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
    }

}
