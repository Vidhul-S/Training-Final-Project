package com.store.inventoryservice.controller;

import com.store.inventoryservice.dto.InventoryRequestDTO;
import com.store.inventoryservice.dto.InventoryResponseDTO;
import com.store.inventoryservice.entities.Inventory;
import com.store.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping("/get-inventory")
    public List<Inventory> getInventory() {
        return inventoryService.getAllInventories();
    }
    @PostMapping("/check-stock")
    public InventoryResponseDTO checkStock(@RequestBody InventoryRequestDTO inventoryRequestDTO) {
        return inventoryService.isInStock(inventoryRequestDTO.getProductId(), inventoryRequestDTO.getRequestedQuantity());
    }
}
