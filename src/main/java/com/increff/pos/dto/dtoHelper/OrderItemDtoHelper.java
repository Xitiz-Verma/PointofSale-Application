package com.increff.pos.dto.dtoHelper;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.OrderItemUpdateForm;
import com.increff.pos.pojo.OrderItemPojo;

public class OrderItemDtoHelper {

    public static OrderItemData convertOrderItemPojotoOrderItemData(OrderItemPojo orderItemPojo)
    {
        OrderItemData orderItemData=new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setBarcode(orderItemPojo.getBarcode());
        orderItemData.setQuantity(orderItemData.getQuantity());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderItemData;
    }

    public static OrderItemPojo convertOrderItemFormToOrderItemPojo(OrderItemForm orderItemForm)
    {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setBarcode(orderItemForm.getBarcode());
        orderItemPojo.setOrderId(orderItemForm.getOrderId());
        orderItemPojo.setSellingPrice(orderItemForm.getSellingPrice());
        orderItemPojo.setQuantiy(orderItemForm.getQuantity());
        return orderItemPojo;
    }

    public static OrderItemPojo convertOrderItemFormToOrderItemPojo(OrderItemUpdateForm orderItemUpdateForm)
    {
        OrderItemPojo orderItemPojo=new OrderItemPojo();
        orderItemPojo.setQuantiy(orderItemUpdateForm.getQuantity());
        orderItemPojo.setSellingPrice(orderItemUpdateForm.getSellingPrice());
        orderItemPojo.setId(orderItemUpdateForm.getId());
        return orderItemPojo;
    }

    public static OrderItemData convertOrderItemPojoToOrderItemData(OrderItemPojo orderItemPojo)
    {
        OrderItemData orderItemData=new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setQuantity(orderItemPojo.getQuantiy());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        orderItemData.setBarcode(orderItemPojo.getBarcode());
        return orderItemData;
    }


}
