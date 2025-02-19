package com.karenqvistlarsen.ecom_proj.controller;

import com.karenqvistlarsen.ecom_proj.model.ParcelDTO;
import com.karenqvistlarsen.ecom_proj.service.ParcelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@AllArgsConstructor
public class ParcelController {
    private final ParcelService service;

    @GetMapping("/parcels")
    public ResponseEntity<List<ParcelDTO>> getAllParcels() {
        return ResponseEntity.ok(service.getAllParcels());
    }

    @GetMapping("/parcel/{id}")
    public ResponseEntity<ParcelDTO> getParcel(@PathVariable int id) {
        return service.getParcelById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/parcel")
    public ResponseEntity<ParcelDTO> addParcel(@RequestBody ParcelDTO parcelDTO) {
        return service.addParcel(parcelDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PostMapping("/parcel/estimate")
    public ResponseEntity<ParcelDTO> estimateParcel(@RequestBody ParcelDTO parcelDTO) {
        return service.estimateParcel(parcelDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/parcel/{id}")
    public ResponseEntity<ParcelDTO> updateParcel(@PathVariable int id,
                                                 @RequestBody ParcelDTO parcelDTO) {
        return service.updateParcel(id, parcelDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/parcel/{id}")
    public ResponseEntity<ParcelDTO> deleteParcel(@PathVariable int id) {
        return service.deleteParcel(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
