package com.increff.pos.dto.dtoHelper;


import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;

public class InventoryDtoHelper {

    public static InventoryData convertInventoryPojotoInventoryData(InventoryPojo inventoryPojo)
    {
        InventoryData inventoryData=new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setBarcode(inventoryPojo.getBarcode());
        inventoryData.setQuantity(inventoryPojo.getQuantity());
        inventoryData.setProductId(inventoryPojo.getProductId());
        return inventoryData;
    }

    public static InventoryPojo convertInventoryFormtoInventoryPojo(InventoryForm inventoryForm)
    {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setBarcode(inventoryForm.getBarcode());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        return inventoryPojo;
    }
}