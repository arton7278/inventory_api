package com.task.inventory_api.repository;

import com.task.inventory_api.domain.Goods;
import com.task.inventory_api.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
