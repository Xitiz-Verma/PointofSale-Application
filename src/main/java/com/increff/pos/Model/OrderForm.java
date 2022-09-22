package com.increff.pos.Model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    @NotNull(message = "OrderId cant be Null")
    private Integer orderId;

    @NotNull(message = "ProductId cant be Null")
    private Integer productId;

    @Min(value = 1,message = "Quantity hould be GreaterThan/EqualTo 1")
    @NotNull(message = "Quantity cant be Null")
    private Integer quantity;

    @Min(value = 0,message = "Selling Price must be greater than 0")
    @NotNull(message = "Selling Price cant be Null")
    private Double sellingPrice;
}
