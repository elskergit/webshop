package com.karenqvistlarsen.ecom_proj.service;

import com.karenqvistlarsen.ecom_proj.exception.ParcelNotFoundException;
import com.karenqvistlarsen.ecom_proj.model.Parcel;
import com.karenqvistlarsen.ecom_proj.model.ParcelDTO;
import com.karenqvistlarsen.ecom_proj.model.ParcelDTOMapper;
import com.karenqvistlarsen.ecom_proj.repository.ParcelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParcelService {

    private final ParcelRepository repo;
    private final ParcelDTOMapper parcelDTOMapper;

    public List<ParcelDTO> getAllParcels() {
        return repo.findAll()
                .stream()
                .map(parcelDTOMapper::parcelToParcelDTO)
                .collect(Collectors.toList());
    }

    public Optional<ParcelDTO> getParcelById(int id) {
        return Optional.ofNullable(repo.findById(id)
                .map(parcelDTOMapper::parcelToParcelDTO)
                .orElseThrow(() -> new ParcelNotFoundException("Parcel with id [%s] not found".formatted(id))));
    }

    public Optional<ParcelDTO> addParcel(ParcelDTO parcelDTO) {
        Parcel parcel = parcelDTOMapper.parcelDTOToParcel(parcelDTO);
        parcel.setRequestPosted(LocalDate.now());
        calculateParcelDetails(parcel);
        Parcel savedParcel = repo.save(parcel);

        ParcelDTO savedParcelDTO = parcelDTOMapper.parcelToParcelDTO(savedParcel);

        return Optional.of(savedParcelDTO);
    }

    public Optional<ParcelDTO> updateParcel(int id, ParcelDTO parcelDTO) {
        return repo.findById(id).map(existingParcel -> {
            existingParcel.setDestinationAddress(parcelDTO.getDestinationAddress());
            existingParcel.setPickUpAddress(parcelDTO.getPickUpAddress());
            existingParcel.setPickUpDate(parcelDTO.getPickUpDate());
            existingParcel.setServiceTier(parcelDTO.getServiceTier());
            existingParcel.setWidth(parcelDTO.getWidth());
            existingParcel.setHeight(parcelDTO.getHeight());
            existingParcel.setWeight(parcelDTO.getWeight());
            existingParcel.setCustomerName(parcelDTO.getCustomerName());

            calculateParcelDetails(existingParcel);

            Parcel savedParcel = repo.save(existingParcel);
            return parcelDTOMapper.parcelToParcelDTO(savedParcel);
        });
    }

    public Optional<ParcelDTO> deleteParcel(int id) {
        ParcelDTO existingParcelDTO = getParcelById(id).orElseThrow(
                () -> new ParcelNotFoundException("Parcel with id [%s] not found".formatted(id))
        );

        repo.deleteById(id);
        return Optional.of(existingParcelDTO);
    }

    public Optional<ParcelDTO> estimateParcel(ParcelDTO parcelDTO) {
         Parcel parcel = parcelDTOMapper.parcelDTOToParcel(parcelDTO);
         calculateParcelDetails(parcel);
         return Optional.of(parcelDTOMapper.parcelToParcelDTO(parcel));
    }

    private void calculateParcelDetails(Parcel parcel) {
        double basePrice = 5.0;
        double weightCost = parcel.getWeight() * 2.0;
        double sizeCost = (parcel.getWidth() + parcel.getHeight()) * 1.5;
        double serviceMultiplier = switch (parcel.getServiceTier().toLowerCase()) {
            case "standard" -> 1.5;
            case "express" -> 2.;
            default -> 1.; // economy
        };

        double finalPrice = (basePrice + weightCost + sizeCost) * serviceMultiplier;
        parcel.setPrice(finalPrice);

        int extraDays = switch (parcel.getServiceTier().toLowerCase()) {
            case "express" -> 1;
            case "standard" -> 3;
            default -> 7; // economy
        };

        LocalDate pickUpDate = parcel.getPickUpDate() != null ? parcel.getPickUpDate() : LocalDate.now();
        parcel.setDeliveryDate(pickUpDate.plusDays(extraDays));
    }
}
