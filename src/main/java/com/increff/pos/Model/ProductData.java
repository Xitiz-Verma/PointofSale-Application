package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductData {

    private Integer id;
    private Integer BrandPojoId;
    private String barcode;

    private String brand;
    private String category;

    private String name;
    private Double mrp;
}