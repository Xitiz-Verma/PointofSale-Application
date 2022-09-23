package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemData {

    private Integer id;

    private Integer orderId;
    private Integer productId;

    @Min(value = 1, message = "quantity must be greater than 1")
    @NotNull(message = "quantity cannot be empty")
    private Integer quantity;

    @Min(value = 0,message = "sellingPrice must be greater than 0")
    @NotNull(message = "Selling Price cant be Null")
    private Double sellingPrice;
}
