package com.store.orderservice.config;

import com.store.orderservice.dto.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8081/api/products")
public interface ProductServiceClient {

    @PostMapping("/get-product")
    ProductResponseDTO getProduct(@RequestBody String id);

    @GetMapping("/get-all-products")
    List<ProductResponseDTO> getAllProducts();
}

