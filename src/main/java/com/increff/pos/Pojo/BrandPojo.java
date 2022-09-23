package com.increff.pos.Pojo;

//javax.persistence v krr skte

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

import static com.increff.pos.Pojo.TableConstants.BRAND_GENERATOR;
import static com.increff.pos.Pojo.TableConstants.BRAND_INITIAL_VALUE;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"brand", "category"})}, name = "pos_brand_pojo")
public class BrandPojo {

    @Id
    @TableGenerator(name = BRAND_GENERATOR, initialValue = BRAND_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = BRAND_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;


}
