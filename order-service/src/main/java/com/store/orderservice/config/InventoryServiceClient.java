package com.store.orderservice.config;

import com.store.orderservice.dto.InventoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "inventory-service", url = "http://localhost:8082/api/inventory")
public interface InventoryServiceClient {

    @PostMapping("/check-stock")
    boolean isInStock(@RequestBody InventoryResponseDTO inventoryResponseDTOS);
}
