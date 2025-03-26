package com.inflearn.toby.data;

import com.inflearn.toby.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class OrderRepository {

    private final EntityManagerFactory emf;

    public OrderRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Order order) {
        // em
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        // transaction start
        transaction.begin();

        try {
            // em.persist()
            em.persist(order);

            // transaction commit
            transaction.commit();
        } catch (RuntimeException e) {
            // transaction rollback
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            // transaction close
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
