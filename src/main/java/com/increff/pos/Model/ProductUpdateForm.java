package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductUpdateForm {

    @NotNull(message = "id cannot be null")
    private Integer id;

    @NotBlank(message="barcode cannot be blank")
    private String barcode;

    @NotNull(message = "brand cannot be null")
    private String brand;

    @NotBlank(message = "Category cannot be null")
    private String category;

    @NotBlank(message= "Name cannot be null")
    private String name;

    @NotNull(message = "Mrp cannot be null")
    private Double mrp;
}
