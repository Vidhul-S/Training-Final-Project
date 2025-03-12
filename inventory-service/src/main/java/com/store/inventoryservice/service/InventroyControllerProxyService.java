package com.store.inventoryservice.service;

import com.store.inventoryservice.dto.InventoryRequestDTO;
import com.store.inventoryservice.dto.InventoryResponseDTO;
import com.store.inventoryservice.entities.Inventory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(name = "inventory-service", url = "http://localhost:8082/api/inventory")
public interface InventroyControllerProxyService {

    @CircuitBreaker(name = "Data Base Issue in inventory-service", fallbackMethod = "FallBackDBIssue")
    @GetMapping("/get-inventory")
    public List<Inventory> getInventory();
    default List<Inventory> FallBackDBIssue(Throwable throwable){
        return List.of(new Inventory(0l,"Not Found",0,0));
    }

    @PostMapping("/check-stock")
    @CircuitBreaker(name = "Stock Not Found in inventory-service", fallbackMethod = "FallBack")
    public InventoryResponseDTO checkStock(@RequestBody InventoryRequestDTO inventoryRequestDTO);

    default InventoryResponseDTO FallBack(InventoryRequestDTO inventoryRequestDTO ,Throwable throwable){
        return new InventoryResponseDTO("Not Found",0,false);
    }
}
