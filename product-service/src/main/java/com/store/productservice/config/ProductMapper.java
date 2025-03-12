package com.store.productservice.config;

import com.store.productservice.dto.ProductRequestDTO;
import com.store.productservice.dto.ProductResponseDTO;
import com.store.productservice.entities.Product;
import com.store.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductMapper {

    private final ProductRepository productRepository;

    @Autowired
    public ProductMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product toEntity(ProductRequestDTO productRequestDTO) {
        return productRepository.findById(productRequestDTO.getId())
                .orElseGet(() ->
                        Product.builder()
                                .id(productRequestDTO.getId())
                                .name(null)
                                .description(null)
                                .price(BigDecimal.ZERO)
                                .build()
                );
    }

    public ProductResponseDTO toDto(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .found(true)
                .build();
    }
}
