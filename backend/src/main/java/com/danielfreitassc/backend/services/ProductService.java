package com.danielfreitassc.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.danielfreitassc.backend.dtos.ProductDTO;
import com.danielfreitassc.backend.mappers.ProductMapper;
import com.danielfreitassc.backend.models.ProductEntity;
import com.danielfreitassc.backend.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductDTO create(ProductDTO productDTO) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDTO)));
    }

    public List<ProductDTO> getList() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    public ProductDTO getById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum produto com este ID");
        return productMapper.toDto(product.get());
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum produto com este ID");
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        productEntity.setId(id);
        return productMapper.toDto(productRepository.save(productEntity));
    }

    public ProductDTO delete(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Nenhum produto com este ID");
        productRepository.delete(product.get());
        return productMapper.toDto(product.get());
    }
}
