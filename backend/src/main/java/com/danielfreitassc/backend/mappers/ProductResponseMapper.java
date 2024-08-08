package com.danielfreitassc.backend.mappers;

import org.mapstruct.Mapper;

import com.danielfreitassc.backend.dtos.ProductResponseDTO;
import com.danielfreitassc.backend.models.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {
    ProductResponseDTO toDto(ProductEntity productEntity);
}
