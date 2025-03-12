package com.store.orderservice.service;

import com.store.orderservice.dto.OrderRequestDTO;
import com.store.orderservice.dto.OrderResponseDTO;
import com.store.orderservice.dto.ProductResponseDTO;
import com.store.orderservice.entities.Order;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@FeignClient(name = "order-service", url = "http://localhost:8083/api/order")
public interface OrderControllerproxyService {
    @CircuitBreaker(name = "Unable To Update Order", fallbackMethod = "FallBackBrokenIssue")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO);

    default ResponseEntity<OrderResponseDTO> FallBackBrokenIssue(OrderRequestDTO orderRequestDTO,Throwable throwable){
        return new ResponseEntity<OrderResponseDTO>(new OrderResponseDTO("Order Failed", new ProductResponseDTO(null,null,null, BigDecimal.ZERO,false)), null, 500);
    }


    @CircuitBreaker(name = "Order Not Found", fallbackMethod = "FallBack")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrderById(@PathVariable String id);

    default ResponseEntity<Optional<Order>> FallBack(String id,Throwable throwable){
        return new ResponseEntity<Optional<Order>>(Optional.empty(), null, 500);
    }

    @CircuitBreaker(name = "Connection Issue with product-service", fallbackMethod = "FallBackDBIssue")
    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts();

    default ResponseEntity<List<ProductResponseDTO>> FallBackDBIssue(Throwable throwable){
        return new ResponseEntity<List<ProductResponseDTO>>(List.of(new ProductResponseDTO("Not Found Any Products",null,null, BigDecimal.ZERO,false)), null, 500);
    }
}
