package com.maxim.hibernate;

import java.sql.SQLException;
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.maxim.hibernate.entity.Role;
import com.maxim.hibernate.entity.User;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
        // BlockingDeque<Connection> pool = null;
        // SessionFactory
        // Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "pass");
        // Session
        Configuration configuration = new Configuration();
        // configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        // configuration.addAnnotatedClass(User.class);
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            User user = User.builder()
                            .username("Poko123")
                            .firstname("Pok")
                            .lastname("Mok")
                            .birthDate(LocalDate.of(1999, 11, 1))
                            .age(14)
                            .role(Role.ADMIN)
                            .build();

            session.persist(user);
            session.getTransaction().commit();
        }
        
    }
}
