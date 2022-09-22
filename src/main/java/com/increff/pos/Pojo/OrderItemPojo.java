package com.increff.pos.Pojo;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.increff.pos.Pojo.TableConstants.ORDER_ITEM_GENERATOR;
import static com.increff.pos.Pojo.TableConstants.ORDER_ITEM_INITIAL_VALUE;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames={"orderId","productId"})},name="pos_order_item_pojo")
public class OrderItemPojo extends AbstractPojo{

    @Id
//    @TableGenerator(name=ORDER_ITEM_GENERATOR,initialValue = ORDER_ITEM_INITIAL_VALUE)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = ORDER_ITEM_GENERATOR)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer orderId;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Integer quantiy;

    @Column(nullable = false)
    private Double sellingPrice;


}
