package com.increff.pos.pojo;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.increff.pos.pojo.TableConstants.ORDER_ITEM_GENERATOR;
import static com.increff.pos.pojo.TableConstants.ORDER_ITEM_INITIAL_VALUE;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"orderId","barcode"})},name="pos_order_item_pojo")
public class OrderItemPojo extends AbstractPojo{

    @Id
    @TableGenerator(name=ORDER_ITEM_GENERATOR,initialValue = ORDER_ITEM_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ORDER_ITEM_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private Integer orderId;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double sellingPrice;


}
