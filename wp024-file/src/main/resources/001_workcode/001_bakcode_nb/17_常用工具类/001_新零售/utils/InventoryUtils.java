package com.zrytech.framework.newshop.utils;

import com.zrytech.framework.newshop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryUtils {

    public static InventoryService inventoryService;

    @Autowired
    public void SetInventoryUtils(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public static void setInventory(Integer id, Integer qty, String type) {
        inventoryService.setInventory(id, qty, type);
    }
}
