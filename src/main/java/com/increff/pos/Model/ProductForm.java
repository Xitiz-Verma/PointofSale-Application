package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductForm {

    @NotBlank(message = "Id can't be Null")
    private Integer id;

    @NotBlank(message = "Name cannot be Null")
    private String name;

    @NotBlank(message = "Bar cannot be Null")
    private String barcode;

    @NotBlank(message = "Brand cannot be NULL")
    private String brand;

    @NotBlank(message = "Category cannot be NULL")
    private String category;

    @NotNull
    Double mrp;
}