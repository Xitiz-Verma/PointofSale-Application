package com.increff.pos.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class AbstractDao {

    @PersistenceContext
    protected EntityManager em;

    public <T> List<T> selectAll(Class<T> pojo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root);

        TypedQuery<T> query = em.createQuery(cr);
        List<T> results = query.getResultList();
        return results;
    }

    public <T> void addAbs(T pojoObject) {
        em.persist(pojoObject);
    }

    public <T> T select(Class<T> pojo, Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(pojo);
        Root<T> root = cr.from(pojo);
        cr.select(root).where(cb.equal(root.get("id"), id));

        TypedQuery<T> query = em.createQuery(cr);
        return getSingle(query);
    }

    public <T> T getSingle(TypedQuery<T> query)
    {
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public EntityManager em() {
        return em;
    }
}
