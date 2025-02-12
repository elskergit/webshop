package com.karenqvistlarsen.ecom_proj.unit;

import com.karenqvistlarsen.ecom_proj.model.Parcel;
import com.karenqvistlarsen.ecom_proj.model.ParcelDTO;
import com.karenqvistlarsen.ecom_proj.model.ParcelDTOMapper;
import com.karenqvistlarsen.ecom_proj.repository.ParcelRepository;
import com.karenqvistlarsen.ecom_proj.service.ParcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {
    @InjectMocks
    private ParcelService parcelService;
    @Mock
    private ParcelRepository parcelRepository;
    @Spy
    private ParcelDTOMapper parcelDTOMapper = Mappers.getMapper(ParcelDTOMapper.class);
    private Parcel parcel;
    private ParcelDTO parcelDTO;
    private Parcel updatedParcel;
    private ParcelDTO updatedParcelDTO;

    @BeforeEach
    void setup() {
        LocalDate requestPosted = LocalDate.parse("2025-02-12", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate pickUpTime = LocalDate.parse("2025-02-13", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate deliveryTime = LocalDate.parse("2025-02-14", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        parcel = new Parcel(
                1, "New York", "Los Angeles", pickUpTime, "Express",
                10.5, 5.0, 2.5, "John Doe", 66.5, deliveryTime, requestPosted
        );

        updatedParcel = new Parcel(
                1, "New York", "Los Angeles", pickUpTime, "Express",
                10.5, 5.0, 2.5, "Jane Doe", 66.5, deliveryTime, requestPosted
        );

        parcelDTO = new ParcelDTO(
                1, "New York", "Los Angeles", pickUpTime, "Express",
                10.5, 5.0, 2.5, "John Doe", 66.5, deliveryTime
        );

        updatedParcelDTO = new ParcelDTO(
                1, "New York", "Los Angeles", pickUpTime, "Express",
                10.5, 5.0, 2.5, "Jane Doe", 66.5, deliveryTime
        );


    }

    @Test
    void shouldReturnAllParcels() {
        List<Parcel> parcels = List.of(parcel);
        when(parcelRepository.findAll()).thenReturn(parcels);

        List<ParcelDTO> result = parcelService.getAllParcels();

        assertThat(result)
                .withFailMessage("Expected a list with one parcel, but got a different size")
                .hasSize(1);

        ParcelDTO resultParcelDTO = result.get(0);

        assertThat(resultParcelDTO.getId()).isEqualTo(parcel.getId());
        assertThat(resultParcelDTO.getDest()).isEqualTo(parcel.getDest());
        assertThat(resultParcelDTO.getSource()).isEqualTo(parcel.getSource());
        assertThat(resultParcelDTO.getPickUpDate()).isEqualTo(parcel.getPickUpDate());
        assertThat(resultParcelDTO.getServiceTier()).isEqualTo(parcel.getServiceTier());
        assertThat(resultParcelDTO.getWidth()).isEqualTo(parcel.getWidth());
        assertThat(resultParcelDTO.getHeight()).isEqualTo(parcel.getHeight());
        assertThat(resultParcelDTO.getWeight()).isEqualTo(parcel.getWeight());
        assertThat(resultParcelDTO.getCustomerName()).isEqualTo(parcel.getCustomerName());
        assertThat(resultParcelDTO.getPrice()).isEqualTo(parcel.getPrice());
        assertThat(resultParcelDTO.getDeliveryDate()).isEqualTo(parcel.getDeliveryDate());
    }

    @Test
    void shouldAddParcel() throws IOException {
        when(parcelRepository.save(any(Parcel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(parcelDTOMapper.parcelToParcelDTO(any(Parcel.class))).thenReturn(parcelDTO);

        Optional<ParcelDTO> addedParcelDTO = parcelService.addParcel(parcelDTO);

        assertThat(addedParcelDTO)
                .withFailMessage("Expected ParcelDTO to be present, but it was empty")
                .isPresent();

        ParcelDTO resultParcelDTO = addedParcelDTO.get();

        assertThat(resultParcelDTO.getId()).isEqualTo(parcel.getId());
        assertThat(resultParcelDTO.getDest()).isEqualTo(parcel.getDest());
        assertThat(resultParcelDTO.getSource()).isEqualTo(parcel.getSource());
        assertThat(resultParcelDTO.getPickUpDate()).isEqualTo(parcel.getPickUpDate());
        assertThat(resultParcelDTO.getServiceTier()).isEqualTo(parcel.getServiceTier());
        assertThat(resultParcelDTO.getWidth()).isEqualTo(parcel.getWidth());
        assertThat(resultParcelDTO.getHeight()).isEqualTo(parcel.getHeight());
        assertThat(resultParcelDTO.getWeight()).isEqualTo(parcel.getWeight());
        assertThat(resultParcelDTO.getCustomerName()).isEqualTo(parcel.getCustomerName());
        assertThat(resultParcelDTO.getPrice()).isEqualTo(parcel.getPrice());
        assertThat(resultParcelDTO.getDeliveryDate()).isEqualTo(parcel.getDeliveryDate());
    }


    @Test
    void shouldAddAndUpdateParcel() throws IOException {
        when(parcelRepository.findById(1)).thenReturn(Optional.of(parcel));

        when(parcelRepository.save(any(Parcel.class))).thenReturn(parcel);
        Optional<ParcelDTO> returnedUpdatedParcelDTO = parcelService.updateParcel(parcel.getId(), updatedParcelDTO);

        assertThat(returnedUpdatedParcelDTO)
                .withFailMessage("Expected parcel to be present, but it was empty")
                .isPresent();

        ParcelDTO resultParcelDTO = returnedUpdatedParcelDTO.get();

        assertThat(resultParcelDTO.getId()).isEqualTo(updatedParcel.getId());
        assertThat(resultParcelDTO.getDest()).isEqualTo(updatedParcel.getDest());
        assertThat(resultParcelDTO.getSource()).isEqualTo(updatedParcel.getSource());
        assertThat(resultParcelDTO.getPickUpDate()).isEqualTo(updatedParcel.getPickUpDate());
        assertThat(resultParcelDTO.getServiceTier()).isEqualTo(updatedParcel.getServiceTier());
        assertThat(resultParcelDTO.getWidth()).isEqualTo(updatedParcel.getWidth());
        assertThat(resultParcelDTO.getHeight()).isEqualTo(updatedParcel.getHeight());
        assertThat(resultParcelDTO.getWeight()).isEqualTo(updatedParcel.getWeight());
        assertThat(resultParcelDTO.getCustomerName()).isEqualTo(updatedParcel.getCustomerName());
        assertThat(resultParcelDTO.getPrice()).isEqualTo(updatedParcel.getPrice());
        assertThat(resultParcelDTO.getDeliveryDate()).isEqualTo(updatedParcel.getDeliveryDate());
    }

    @Test
    void shouldAddAndDeleteParcel() throws IOException {
        when(parcelRepository.save(any(Parcel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Optional<ParcelDTO> addedParcelDTO = parcelService.addParcel(parcelDTO);

        assertThat(addedParcelDTO)
                .withFailMessage("Expected parcel to be present, but it was empty")
                .isPresent();

        when(parcelRepository.findById(1)).thenReturn(Optional.of(parcel));
        doNothing().when(parcelRepository).deleteById(1);

        Optional<ParcelDTO> deletedParcelDTO = parcelService.deleteParcel(1);

        assertThat(deletedParcelDTO)
                .withFailMessage("Expected parcel to be present, but it was empty")
                .isPresent();

        ParcelDTO resultDeletedParcelDTO = deletedParcelDTO.get();
        ParcelDTO resultAddedParcelDTO = addedParcelDTO.get();

        assertThat(resultDeletedParcelDTO.getId()).isEqualTo(resultAddedParcelDTO.getId());
        assertThat(resultDeletedParcelDTO.getDest()).isEqualTo(resultAddedParcelDTO.getDest());
        assertThat(resultDeletedParcelDTO.getSource()).isEqualTo(resultAddedParcelDTO.getSource());
        assertThat(resultDeletedParcelDTO.getPickUpDate()).isEqualTo(resultAddedParcelDTO.getPickUpDate());
        assertThat(resultDeletedParcelDTO.getServiceTier()).isEqualTo(resultAddedParcelDTO.getServiceTier());
        assertThat(resultDeletedParcelDTO.getWidth()).isEqualTo(resultAddedParcelDTO.getWidth());
        assertThat(resultDeletedParcelDTO.getHeight()).isEqualTo(resultAddedParcelDTO.getHeight());
        assertThat(resultDeletedParcelDTO.getWeight()).isEqualTo(resultAddedParcelDTO.getWeight());
        assertThat(resultDeletedParcelDTO.getCustomerName()).isEqualTo(resultAddedParcelDTO.getCustomerName());
        assertThat(resultDeletedParcelDTO.getPrice()).isEqualTo(resultAddedParcelDTO.getPrice());
        assertThat(resultDeletedParcelDTO.getDeliveryDate()).isEqualTo(resultAddedParcelDTO.getDeliveryDate());
    }
}

