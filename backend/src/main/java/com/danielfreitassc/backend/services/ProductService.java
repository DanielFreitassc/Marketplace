package com.danielfreitassc.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.dtos.ProductDTO;
import com.danielfreitassc.backend.dtos.ProductResponseDTO;
import com.danielfreitassc.backend.mappers.ProductMapper;
import com.danielfreitassc.backend.mappers.ProductResponseMapper;
import com.danielfreitassc.backend.models.ProductEntity;
import com.danielfreitassc.backend.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductResponseMapper productResponseMapper;

    public ProductResponseDTO create(ProductDTO productDTO) {
        return productResponseMapper.toDto(productRepository.save(productMapper.toEntity(productDTO)));
    }

    public List<ProductResponseDTO> getList() {
        return productRepository.findAll().stream().map(productResponseMapper::toDto).toList();
    }

    public ProductResponseDTO getById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum produto com este ID");
        return productResponseMapper.toDto(product.get());
    }

    public ProductResponseDTO update(Long id, ProductDTO productDTO) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum produto com este ID");
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        productEntity.setId(id);
        return productResponseMapper.toDto(productRepository.save(productEntity));
    }

    public ProductResponseDTO delete(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum produto com este ID");
        productRepository.delete(product.get());
        return productResponseMapper.toDto(product.get());
    }
}
