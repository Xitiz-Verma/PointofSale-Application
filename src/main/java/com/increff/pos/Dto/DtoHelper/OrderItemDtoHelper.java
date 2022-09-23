package com.increff.pos.Dto.DtoHelper;

import com.increff.pos.Model.OrderItemData;
import com.increff.pos.Pojo.OrderItemPojo;

public class OrderItemDtoHelper {

    public static OrderItemData convertOrderItemPojotoOrderItemData(OrderItemPojo orderItemPojo)
    {
        OrderItemData orderItemData=new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setQuantity(orderItemData.getQuantity());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderItemData;
    }
}
