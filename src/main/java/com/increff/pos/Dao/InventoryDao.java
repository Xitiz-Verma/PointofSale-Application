package com.increff.pos.Dao;


import com.increff.pos.Pojo.InventoryPojo;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDao extends AbstractDao{
    private final String SELECT_BY_BARCODE = "select p from InventoryPojo p where barcode=:barcode";

    public InventoryPojo selectByBarcode(String  barcode)
    {
        TypedQuery<InventoryPojo> query = em().createQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }
}
