package com.increff.pos.Model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
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

    public OrderItemDataList(List<OrderItemData> orderItemDataList, ZonedDateTime time, Double total, Integer orderId)
    {


    }

}
