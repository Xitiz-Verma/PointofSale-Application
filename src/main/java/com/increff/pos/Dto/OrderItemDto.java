package com.increff.pos.Dto;

import com.increff.pos.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemDto {

    @Autowired
    private OrderService orderService;

}
