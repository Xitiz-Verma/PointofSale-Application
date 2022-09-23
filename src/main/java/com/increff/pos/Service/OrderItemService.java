package com.increff.pos.Service;

import com.increff.pos.Dao.OrderDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Pojo.OrderItemPojo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = ApiException.class)
@Service
public class OrderItemService {

    private OrderDao orderDao;


    public List<OrderItemPojo> selectFromOrderId(Integer orderId)
    {
        return selectFromOrderId(orderId);
    }
}
