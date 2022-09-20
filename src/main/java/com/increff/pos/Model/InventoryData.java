package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InventoryData {

    @NotNull(message="Id cannot be Null")
    private Integer id;

    @NotNull(message = "Product Id cannot be Null")
    private Integer productId;

    @NotBlank(message = "Barcode cannnot be Blank")
    private String barcode;

    @NotNull(message = "Quantity cannot be Null")
    private Integer quantity;

}
