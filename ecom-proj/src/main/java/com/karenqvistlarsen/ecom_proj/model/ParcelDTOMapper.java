package com.karenqvistlarsen.ecom_proj.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParcelDTOMapper {

    ParcelDTO parcelToParcelDTO(Parcel parcel);

    @Mapping(target = "requestPosted", ignore = true)
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "deliveryDate", ignore = true)
    Parcel parcelDTOToParcel(ParcelDTO parcelDTO);
}
