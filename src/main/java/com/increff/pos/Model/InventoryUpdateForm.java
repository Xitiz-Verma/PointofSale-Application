package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InventoryUpdateForm {

    @NotNull(message="id cant be null")
    private Integer id;

    @NotNull(message="Id cant be Null")
    private Integer quantity;

}
