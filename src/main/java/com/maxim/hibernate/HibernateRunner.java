package com.maxim.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxim.hibernate.entity.User;
import com.maxim.hibernate.util.HibernateUtils;

public class HibernateRunner {

    private static final Logger logger = LoggerFactory.getLogger(HibernateRunner.class);
    public static void main(String[] args) throws SQLException {
        User user = User.builder()
                        .username("Piter@gmail.com")
                        .lastname("Bobson")
                        .firstname("Piter")
                        .build();
        logger.info("user entity is in transient state, object: {}", user);

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();
                logger.trace("Transaction was create, {}", transaction);

                session1.merge(user);
                logger.trace("Use in persistent state, {}, {}", user, session1);

                session1.getTransaction().commit();
            }
            logger.warn("User is in detached state: {}, session is closed {}", user, session1);
        } catch (Exception e) {
            logger.error("Exception occurred", e);
            throw e;
        }
        
    }



    public static Map<String, String> getJson() {

        Map<String, String> json = new HashMap<>();
        json.put("name", "John");
        json.put("id", "25");
        return json;
    }
}
