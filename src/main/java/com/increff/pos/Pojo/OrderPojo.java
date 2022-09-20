package com.increff.pos.Pojo;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.ZonedDateTime;

import static com.increff.pos.Pojo.TableConstants.ORDER_GENERATOR;
import static com.increff.pos.Pojo.TableConstants.ORDER_INTIAL_VALUE;

@Getter
@Setter
@Entity
public class OrderPojo extends AbstractPojo{

    @TableGenerator(name=ORDER_GENERATOR, initialValue = ORDER_INTIAL_VALUE)
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator = ORDER_GENERATOR)
    private Integer id;

    @Column(nullable = false)
    private ZonedDateTime time;

    @Column(nullable = false)
    private Boolean orderPlaced=false;
}
