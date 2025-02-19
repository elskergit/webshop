package com.karenqvistlarsen.ecom_proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karenqvistlarsen.ecom_proj.model.Product;

@Repository
public interface ReviewRepository extends JpaRepository<Product, Integer> {
}