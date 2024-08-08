package com.danielfreitassc.backend.dtos;

public record ProductResponseDTO(
    Long id, 
    String name,
    String description,
    Double price,
    int quantity
) {
    
}
