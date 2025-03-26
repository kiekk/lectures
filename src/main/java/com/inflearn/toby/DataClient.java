package com.inflearn.toby;

import com.inflearn.toby.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class DataClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory.class);

        // em
        EntityManager em = emf.createEntityManager();

        // transaction start
        em.getTransaction().begin();

        // em.persist()
        Order order = Order.create("100", BigDecimal.TEN);

        System.out.println("before create order : " + order); // before create order : Order{id=null, no='100', total=10}

        em.persist(order);

        System.out.println("after create order : " + order); // after create order : Order{id=1, no='100', total=10}

        // transaction end
        em.getTransaction().commit();
        em.close();
    }
}
