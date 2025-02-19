package com.karenqvistlarsen.ecom_proj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.karenqvistlarsen.ecom_proj.model.Review;



@Service
public class ReviewService {

    
    private final List<Review> reviews;
    private int nextId = 6;


    public ReviewService() {
        this.reviews = new ArrayList<>(List.of(
            new Review(1, "John Doe", 5, "I couldn't believe how fast my parcel arrived."),
            new Review(2, "Jane Smith", 5, "Incredible experience! The service was reliable and safe. Highly recommended."),
            new Review(3, "Eric Aubry", 5, "DSV - Go home"),
            new Review(4, "Karen Qvist", 1, "Package teleported onto my face"),
            new Review(5, "Anders Lind", 3, "The service is really bad... However, the website sort of makes up for it due to being super nice and responsive 10/10")
            
        ));
    }


    public List<Review> getReviews() {
        return this.reviews;
    }

    public Review addReview(Review review) {
        review.setId(this.nextId++); // Assign a unique ID
        this.reviews.add(review);
        return review; // Return the newly added review
    }
}
