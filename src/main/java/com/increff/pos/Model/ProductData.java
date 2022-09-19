package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductData {

    @NotNull(message = "Id can't be Null")
    private Integer id;

    @NotNull(message = "BrandPojo Id cannot be Null")
    private Integer BrandPojoId;

    @NotNull(message = "Bar cannot be Null")
    private String barcode;

    @NotNull(message = "Brand cannot be NULL")
    private String brand;

    @NotNull(message = "Category cannot be NULL")
    private String category;

    @NotNull(message = "Name cannot be NULL")
    private String name;

    @NotNull(message = "Mrp cannot be NULL")
    private Double mrp;
}