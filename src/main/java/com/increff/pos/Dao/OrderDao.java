package com.increff.pos.Dao;

import com.increff.pos.Pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends AbstractDao{

    private final static String SELECT_BY_FROM_TO_DATE ="select p from OrderPojo p where time>=:from and time<=:to";

    public void add(OrderPojo orderPojo)
    {
        addAbs(orderPojo);
    }

    public OrderPojo select(int id)
    {
        return select(OrderPojo.class,id);
    }

    public List<OrderPojo> selectAll()
    {
        return selectAll(OrderPojo.class);
    }


}
