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
                                .name("Bobo")
                                .build();
        User user = User.builder()
                        .username("Mishan@gmail.com")
                        .personalInfo(PersonalInfo.builder()
                                                    .firstname("Micka")
                                                    .lastname("Popokao")
                                                    .birthDate(new Birthday(LocalDate.of(1994, 11, 4)))
                                                    .build())
                        .company(company)
                        .build();

        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

                User user2 = session1.get(User.class, 7L);
                Company company2 = user2.getCompany();
                company2.getName();

                transaction.commit();
            }
        }
    }
}
