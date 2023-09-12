package com.maxim.hibernate;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
        // BlockingDeque<Connection> pool = null;
        // SessionFactory
        // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "pass");
        // Session
        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            System.out.println("OK");
        }
        
    }
}
