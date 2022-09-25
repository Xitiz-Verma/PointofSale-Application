package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@XmlRootElement
public class OrderItemDataList {

    private List<OrderItemData> orderItemDataList;
    private String time;
    private Double total;
    private Integer orderId;

    public OrderItemDataList()
    {

    }

    public OrderItemDataList(List<OrderItemData> orderItemDataList2, ZonedDateTime time, Double total, Integer orderId)
    {
        this.orderItemDataList = new ArrayList<OrderItemData>();
        this.time = time.toLocalDate().toString()+" "+time.toLocalTime().toString();
        this.orderId=orderId;
        this.total=total;

        for(OrderItemData orderItemData : orderItemDataList2)
        {
            this.orderItemDataList.add(orderItemData);
        }

    }

    public List<OrderItemData> getOrderItem()
    {
        return orderItemDataList;
    }

}
