package com.increff.pos.Dto.DtoHelper;

import com.increff.pos.Model.OrderData;
import com.increff.pos.Pojo.OrderPojo;

public class OrderDtoHelper {

    public static OrderData convertOrderPojotoOrderData(OrderPojo orderPojo)
    {
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setTime(orderPojo.getTime().toString());
        orderData.setOrderPlaced(orderPojo.getOrderPlaced());
        return orderData;
    }
}
