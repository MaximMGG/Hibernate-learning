package com.maxim.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.maxim.hibernate.entity.User;
import com.maxim.hibernate.util.HibernateUtils;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
        User user = User.builder()
                        .username("Piter@gmail.com")
                        .lastname("Bobson")
                        .firstname("Piter")
                        .build();

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.merge(user);

                session1.getTransaction().commit();
            }

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("Paul");
                // session2.remove(user);

                session2.refresh(user);

                session2.getTransaction().commit();
            }
        }
        
    }



    public static Map<String, String> getJson() {

        Map<String, String> json = new HashMap<>();
        json.put("name", "John");
        json.put("id", "25");
        return json;
    }
}
