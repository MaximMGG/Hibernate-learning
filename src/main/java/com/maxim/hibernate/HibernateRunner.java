package com.maxim.hibernate;

import java.sql.SQLException;
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.maxim.hibernate.converter.BirthdayConvertor;
import com.maxim.hibernate.entity.Birthday;
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
        configuration.addAttributeConverter(new BirthdayConvertor());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            User user = User.builder()
                            .username("Ubro1")
                            .firstname("Mick")
                            .lastname("Lubo")
                            .birthDate(new Birthday(LocalDate.of(1979, 1, 11)))
                            .role(Role.USER)
                            .build();

            session.persist(user);
            session.getTransaction().commit();
        }
        
    }
}
