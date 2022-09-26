package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
public class SalesReport {

    private String brand;
    private String category;
    private Integer quantity;
    private Double revenue;

    //Default Constructor
    public SalesReport()
    {

    }

    //Param Constructor
    public SalesReport(String brand,String category,Integer quantity,Double revenue)
    {
        DecimalFormat decimalFormat=new DecimalFormat("#.##");
        this.brand=brand;
        this.category=category;
        this.quantity=quantity;
        this.revenue=revenue;
    }
}
