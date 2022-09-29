package com.increff.pos.dao;


import com.increff.pos.model.InventoryReport;
import com.increff.pos.pojo.InventoryPojo;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{
    private final String SELECT_BY_BARCODE = "select p from InventoryPojo p where barcode=:barcode";
    private final String SELECT_BY_PRODUCT_ID = "select p from InventoryPojo p where productId= :productId";

    private final String SELECT_INVENTORY_REPORT = "select new"+
            "pos.model.InventoryReport(p.brand,p.category,i.quamtity) from ProductPojo p,InventoryPojo i where"+
            "p.id=i.productId";
    public void add(InventoryPojo inventoryPojo)
    {
        addAbs(inventoryPojo);
    }
    public InventoryPojo select(int id)
    {
        return select(InventoryPojo.class,id);
    }

    public List<InventoryPojo> selectAll()
    {
        return selectAll(InventoryPojo.class);
    }
    public InventoryPojo selectByBarcode(String  barcode)
    {
        TypedQuery<InventoryPojo> query = em().createQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }

    public InventoryPojo selectByProductId(int productId)
    {
        TypedQuery<InventoryPojo> query=em().createQuery(SELECT_BY_PRODUCT_ID, InventoryPojo.class);
        query.setParameter("productId",productId);
        InventoryPojo inventoryPojo = getSingle(query);
        return inventoryPojo;
    }

    public List<InventoryReport> getInventoryReport()
    {
        TypedQuery<InventoryReport> query=em().createQuery(SELECT_INVENTORY_REPORT,InventoryReport.class);
        return query.getResultList();
    }

    public void update()
    {
        //Symbolic
    }

}
