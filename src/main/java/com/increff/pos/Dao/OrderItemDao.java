package com.increff.pos.Dao;

import com.increff.pos.Pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static final String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:orderId";

    private static final String SELECT_BY_PRODUCT_ID = "select p from OrderItemPojo p where productId=:productId";

    private static final String SELECT_BY_ORDER_ID_PRODUCT_ID = "select p from OrderItemPojo p where productId=:productId and orderId=:orderId";

    private static final String SELECT_BY_ORDER_ID_LIST = "select p from OrderItemPojo p where orderId in :orderIdList";



    public List<OrderItemPojo> selectFromOrderId(int orderId)
    {
        TypedQuery<OrderItemPojo> query=em().createQuery(SELECT_BY_ORDER_ID,OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }
}
