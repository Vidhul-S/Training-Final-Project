package com.store.productservice.service;

import com.store.productservice.config.ProductMapper;
import com.store.productservice.config.ProductNotificationProducer;
import com.store.productservice.dto.ProductNotificationDTO;
import com.store.productservice.dto.ProductRequestDTO;
import com.store.productservice.dto.ProductResponseDTO;
import com.store.productservice.entities.Product;
import com.store.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductNotificationProducer notificationProducer;

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public Optional<ProductResponseDTO> findProductById(String productId) {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty()) {
            ProductNotificationDTO dto = ProductNotificationDTO.builder()
                    .productId(productId)
                    .message("❌ Product ID: " + productId + " not found! Suggest adding it.")
                    .build();
            notificationProducer.sendProductSuggestion(dto);
        }
        return productOpt.map(product -> ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build());
    }

    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {
        Product product = productMapper.toEntity(productRequestDTO);
        productRepository.save(product);

        ProductNotificationDTO dto = ProductNotificationDTO.builder()
                .productId(product.getId())
                .message("✅ New Product Added: " + product.getName())
                .build();
        notificationProducer.sendProductAddedNotification(dto);
        return productMapper.toDto(product);
    }
}
