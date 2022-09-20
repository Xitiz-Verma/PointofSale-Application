package com.increff.pos.Dto;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.OrderData;
import com.increff.pos.Pojo.OrderPojo;
import com.increff.pos.Service.OrderItemService;
import com.increff.pos.Service.OrderService;

import com.increff.pos.Service.BrandService;
import com.increff.pos.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.Dto.DtoHelper.OrderDtoHelper.convertOrderPojotoOrderData;

@Service
public class OrderDto {
    //private final Fopfactory fopfactory=FopFactory.newInstance(new file(".").toURI());

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    BrandService brandService;
    @Autowired
    ProductService productService;


    public OrderData get(Integer id)throws ApiException{
        return convertOrderPojotoOrderData(orderService.get(id));
    }

    public List<OrderData> getAll()throws ApiException
    {
        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderData> orderDataList = new ArrayList<>();
        for(OrderPojo orderPojo : orderPojoList)
        {
            orderDataList.add(convertOrderPojotoOrderData(orderPojo));
        }
        return orderDataList;
    }

    public Integer updateOrderStatusPlaced(Integer id)throws ApiException
    {
        orderService.updateOrderStatusPlaced(id);
        return id;
    }

    public ZonedDateTime add()throws ApiException
    {
        return orderService.add();
    }
}
