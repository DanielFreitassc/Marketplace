package com.danielfreitassc.backend.mappers;

import org.mapstruct.Mapper;

import com.danielfreitassc.backend.dtos.ProductDTO;
import com.danielfreitassc.backend.models.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(ProductEntity productEntity);
    ProductEntity toEntity(ProductDTO productDTO);
}
