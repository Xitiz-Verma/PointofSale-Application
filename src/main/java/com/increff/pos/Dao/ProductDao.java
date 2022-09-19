package com.increff.pos.Dao;

import com.increff.pos.Pojo.ProductPojo;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractDao{

    private final static String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:barcode";
    private final static String SELECT_BY_BARCODE_AND_NOT_EQUAL_ID = "select p form ProductPojo p where barcode=:barcode nd id!=:id";
    private final static String SELECT_BY_BRAND_ID = "select p form ProductPojo p where brandID=:brandId";
    private final static String SELECT_BY_BRAND = "select p from ProductPojo p where brand=:brand";
    private final static String SELECT_BY_CATEGORY = "select p from ProductPojo where category=:category";
    private final static String SELECT_BY_BRAND_CATEGORY = "select p from ProductPojo where brand=:brand and category=:category";

    public void add(ProductPojo productPojo){
        addAbs(productPojo);
    }

    public ProductPojo selectByBarcode(String barcode)
    {
        TypedQuery<ProductPojo> query = em().createQuery(SELECT_BY_BARCODE, ProductPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }
    public List<ProductPojo> selectByBrandId(int brandId)
    {
        TypedQuery<ProductPojo> query=em().createQuery(SELECT_BY_BRAND_ID, ProductPojo.class);
        query.setParameter("brandId",brandId);
        return query.getResultList();
    }
}
