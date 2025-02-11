package com.karenqvistlarsen.ecom_proj.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

    @Mapping(source = "count", target = "stockQuantity")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "stockQuantity", target = "count")
    @Mapping(target = "lastUpdatedBy", ignore = true)
    Product productDTOToProduct(ProductDTO productDTO);
}
