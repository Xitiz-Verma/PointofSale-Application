package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryData {

    
    private Integer id;

    private Integer productId;

    private String barcode;

    private Integer quantity;

}
