package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemData {

    private Integer id;

    private Integer orderId;
    private Integer productId;

    private Integer quantity;
    private Double sellingPrice;
}
