package com.store.inventoryservice.service;

import com.store.inventoryservice.dto.InventoryResponseDTO;
import com.store.inventoryservice.dto.InventoryNotificationDTO;
import com.store.inventoryservice.entities.Inventory;
import com.store.inventoryservice.config.InventoryNotificationProducer;
import com.store.inventoryservice.repository.InventoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryNotificationProducer notificationProducer;

    public InventoryResponseDTO isInStock(String productId, int requestedQuantity) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductId(productId);
        if (inventoryOpt.isEmpty()) {
            return InventoryResponseDTO.builder()
                    .productId(productId)
                    .requestedQuantity(requestedQuantity)
                    .inStock(false)
                    .build();
        }
        Inventory inventory = inventoryOpt.get();
        boolean available = inventory.getStockQuantity() >= requestedQuantity;
        InventoryNotificationDTO dto = InventoryNotificationDTO.builder()
                .productId(productId)
                .stockQuantity(inventory.getStockQuantity())
                .message("📦 Inventory updated for Product ID: " + productId)
                .build();
        inventory.setStockQuantity(inventory.getStockQuantity() - requestedQuantity);
        inventoryRepository.save(inventory);
        if (inventory.getStockQuantity() < inventory.getMinimumStock()) {
            dto.setMessage("⚠️ LOW STOCK ALERT! Product ID: " + productId + " | Remaining: " + inventory.getStockQuantity());
        }
            notificationProducer.sendLowStockNotification(dto);
        return InventoryResponseDTO.builder()
                .productId(productId)
                .requestedQuantity(requestedQuantity)
                .inStock(available)
                .build();
        }
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }
}
