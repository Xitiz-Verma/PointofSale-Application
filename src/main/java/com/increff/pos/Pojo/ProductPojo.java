package com.increff.pos.Pojo;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.increff.pos.Pojo.TableConstants.PRODUCT_GENERATOR;
import static com.increff.pos.Pojo.TableConstants.PRODUCT_INITIAL_VALUE;


@Getter
@Setter
@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})})
public class ProductPojo extends AbstractPojo {

//    @TableGenerator(name = PRODUCT_GENERATOR, initialValue = PRODUCT_INITIAL_VALUE)
    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = PRODUCT_GENERATOR)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer brandId;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double mrp;


}
