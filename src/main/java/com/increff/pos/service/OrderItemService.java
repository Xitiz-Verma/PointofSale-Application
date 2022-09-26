package com.increff.pos.service;

import com.google.protobuf.Api;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Transactional(rollbackFor = ApiException.class)
@Service
public class OrderItemService {

    private OrderItemDao orderItemDao;

    public void add(OrderItemPojo orderItemPojo)throws ApiException
    {
      if(orderItemPojo.getQuantiy()<=0)
        {
            throw new ApiException("Quantity must be greater than 0");
        }
        orderItemDao.add(orderItemPojo);
    }

    public List<OrderItemPojo> getAll()throws ApiException
    {
        return orderItemDao.selectAll();
    }

    public OrderItemPojo get(Integer id)throws ApiException
    {
        return getCheck(id);

    }

    public OrderItemPojo getCheck(Integer id)throws ApiException
    {
        OrderItemPojo orderItemPojo=orderItemDao.select(id);
        if(isNull(orderItemPojo))
        {
            throw new ApiException("OrderItem with given Id does Not exist, id : "+id);
        }
        return orderItemPojo;
    }

    public void update(OrderItemPojo orderItemPojo)throws ApiException
    {
        getCheck(orderItemPojo.getId());
        OrderItemPojo orderItemPojo2=orderItemDao.select(orderItemPojo.getId());
        orderItemPojo2.setQuantiy(orderItemPojo.getQuantiy());
        orderItemPojo2.setSellingPrice(orderItemPojo.getSellingPrice());
        orderItemDao.update();//symbolic
    }

    public void delete(Integer id)throws ApiException
    {
        getCheck(id);
        orderItemDao.delete(id);
    }


    public List<OrderItemPojo> selectFromOrderId(Integer orderId)
    {
        return orderItemDao.selectFromOrderId(orderId);
    }

    public OrderItemPojo selectFromOrderIdBarcode(Integer orderId,String barcode)
    {
        return orderItemDao.selectFromOrderIdBarcode(orderId,barcode);
    }

}
