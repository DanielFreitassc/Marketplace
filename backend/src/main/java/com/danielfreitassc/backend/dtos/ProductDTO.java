package com.danielfreitassc.backend.dtos;

public record ProductDTO( 
    String name,
    String description,
    Double price,
    int quantity
) {
    
}
