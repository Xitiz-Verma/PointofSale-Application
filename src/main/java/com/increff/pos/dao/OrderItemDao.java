package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static final String DELETE="delete from OrderItemPojo where id=:id";

    private static final String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:orderId";

    private static final String SELECT_BY_BARCODE = "select p from OrderItemPojo p where barcode=:barcode";

    private static final String SELECT_BY_ORDER_ID_BARCODE = "select p from OrderItemPojo p where barcode=:barcode and orderId=:orderId";

    private static final String SELECT_BY_ORDER_ID_LIST = "select p from OrderItemPojo p where orderId in :orderIdList";


    public void add(OrderItemPojo orderItemPojo)
    {
        addAbs(orderItemPojo);
    }

    public List<OrderItemPojo> selectAll()
    {
        return selectAll(OrderItemPojo.class);
    }

    public OrderItemPojo select(int id)
    {
        return select(OrderItemPojo.class,id);
    }

    public List<OrderItemPojo> selectFromOrderId(int orderId)
    {
        TypedQuery<OrderItemPojo> query=em().createQuery(SELECT_BY_ORDER_ID,OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }

    public OrderItemPojo selectFromOrderIdBarcode(Integer orderId,String barcode)
    {
        TypedQuery<OrderItemPojo> query=em().createQuery(SELECT_BY_ORDER_ID_BARCODE, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }

    public void update()
    {
        //Symbolic
    }

    public int delete(int id)
    {
        Query query = em().createQuery(DELETE);
        query.setParameter("id",id);
        return query.executeUpdate();

    }
}
