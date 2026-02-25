package com.maniraj.inventoryservice.service;

import com.maniraj.inventoryservice.model.Inventory;
import com.maniraj.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }
}
