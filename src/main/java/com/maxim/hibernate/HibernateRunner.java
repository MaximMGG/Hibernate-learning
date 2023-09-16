package com.maxim.hibernate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.maxim.hibernate.entity.Birthday;
import com.maxim.hibernate.entity.PersonalInfo;
import com.maxim.hibernate.entity.User;
import com.maxim.hibernate.util.HibernateUtils;

public class HibernateRunner {

    // private static Logger log = LogManager.getLogger(HibernateRunner.class.getName());
    private static final Logger logger = LogManager.getLogger(HibernateRunner.class);
    public static void main(String[] args) throws SQLException {
        User user = User.builder()
                        .username("Poul@gmail.com")
                        .personalInfo(PersonalInfo.builder()
                                                    .firstname("Poul")
                                                    .lastname("Lorenso")
                                                    .birthDate(new Birthday(LocalDate.of(1994, 11, 4)))
                                                    .build())
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
