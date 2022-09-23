package com.increff.pos.Dao;

import com.increff.pos.Pojo.BrandPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao {

    private final static String SELECT_BY_BRAND_CATEGORY = "select p from BrandPojo p where brand = : brand and category = : category";
    private final static String SELECT_BY_BRAND = "select p from BrandPojo p where brand=:brand";
    private final static String SELECT_BY_CATEGORY = "select p form BrandPojo p were category=:category";


    public List<BrandPojo> selectAll() {
        return selectAll(BrandPojo.class);
    }

    public void add(BrandPojo p) {
        addAbs(p);
    }

    public BrandPojo select(int id) {
        return select(BrandPojo.class, id);
    }

    public BrandPojo selectByBrandCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }

    public BrandPojo selectByBrand(String brand) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND, BrandPojo.class);
        query.setParameter("brand", brand);
        return getSingle(query);
    }

    public BrandPojo selectByCategory(String category) {
        TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_CATEGORY, BrandPojo.class);
        query.setParameter("category", category);
        return getSingle(query);
    }

    public void update() {

    }
}
