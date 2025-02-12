package com.karenqvistlarsen.ecom_proj.repository;

import com.karenqvistlarsen.ecom_proj.model.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Integer> {
}
