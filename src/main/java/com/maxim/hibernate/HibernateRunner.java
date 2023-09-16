package com.maxim.hibernate;

import java.sql.SQLException;
import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.maxim.hibernate.entity.Birthday;
import com.maxim.hibernate.entity.Company;
import com.maxim.hibernate.entity.PersonalInfo;
import com.maxim.hibernate.entity.User;
import com.maxim.hibernate.util.HibernateUtils;

public class HibernateRunner {

    // private static Logger log = LogManager.getLogger(HibernateRunner.class.getName());
    // private static final Logger logger = LogManager.getLogger(HibernateRunner.class);
    public static void main(String[] args) throws SQLException {
        Company company = Company.builder()
                                .name("Amazon")
                                .build();
        User user = User.builder()
                        .username("Bil@gmail.com")
                        .personalInfo(PersonalInfo.builder()
                                                    .firstname("Bil")
                                                    .lastname("Carry")
                                                    .birthDate(new Birthday(LocalDate.of(1988, 10, 1)))
                                                    .build())
                        .build();

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

                Company c = session1.get(Company.class, 14);
                user.setCompany(c);

                session1.merge(user);

                transaction.commit();
            }
        }
    }
}
