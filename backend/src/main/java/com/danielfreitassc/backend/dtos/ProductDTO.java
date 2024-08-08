package com.danielfreitassc.backend.dtos;

public record ProductDTO(
    Long id, 
    String name,
    String description,
    Double price,
    int quantity
) {
    
}
