package com.store.productservice.service;

import com.store.orderservice.dto.OrderResponseDTO;
import com.store.productservice.dto.ProductRequestDTO;
import com.store.productservice.dto.ProductResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Service
@FeignClient(name = "order-service", url = "http://localhost:8083/api/order")
public interface ProductControllerService {
    @CircuitBreaker(name = "Unable To Add Product", fallbackMethod = "FallBackDbIssue")
    @PostMapping("/add-product")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) ;

    @CircuitBreaker(name = "Unable To Get Product", fallbackMethod = "FallBackDbIssue")
    @PostMapping("/get-product")
    public ResponseEntity<ProductResponseDTO> getProduct(@RequestBody ProductRequestDTO productRequestDTO);

    @CircuitBreaker(name = "Unable To Get All Product", fallbackMethod = "FallBackDbIssue")
    @DeleteMapping("delete/{id}")
    public void deleteProduct(@PathVariable String id);

    default ResponseEntity<ProductResponseDTO> FallBackDbIssue(Throwable throwable){
        return ResponseEntity.notFound().build();
    }

    @CircuitBreaker(name = "Unable To Get All Product", fallbackMethod = "FallBackDbIssue1")
    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts();

    default ResponseEntity<List<ProductResponseDTO>> FallBackDbIssue1(Throwable throwable){
        return ResponseEntity.notFound().build();
    }

}
