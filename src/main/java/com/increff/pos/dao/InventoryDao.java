package com.increff.pos.dao;


import com.increff.pos.model.InventoryReport;
import com.increff.pos.pojo.InventoryPojo;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{
    private final String SELECT_BY_BARCODE = "select p from InventoryPojo p where barcode=:barcode";

    //private final String SELECT_INVENTORY_REPORT = "select b.brand,b.category,p.name,i.quantity from BrandPojo b,ProductPojo p,InventoryPojo i where p.barcode=i.barcode and b.id=p.brandCategoryId";

    private final String SELECT_INVENTORY_REPORT = "select BrandPojo.brand,BrandPojo.category,ProductPojo.name,InventoryPojo.quantity from ((BrandPojo inner join ProductPojo on BrandPojo.id=ProductPojo.brandCategoryId)"+
            " inner join InventoryPojo on InventoryPojo.barcode=ProductPojo.barcode) ";


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
