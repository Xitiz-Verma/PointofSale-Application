package com.increff.pos.Pojo;

//javax.persistence v krr skte

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.increff.pos.Pojo.TableConstants.BRAND_GENERATOR;
import static com.increff.pos.Pojo.TableConstants.BRAND_INITIAL_VALUE;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"brand", "category"})}, name = "brand")
public class BrandPojo {

    @Id
    @TableGenerator(name = BRAND_GENERATOR, initialValue = BRAND_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = BRAND_GENERATOR)
    private int id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String category;


}
