package com.maxim.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import com.maxim.hibernate.entity.Birthday;
import com.maxim.hibernate.entity.Chat;
import com.maxim.hibernate.entity.Company;
import com.maxim.hibernate.entity.LocaleInfo;
import com.maxim.hibernate.entity.PersonalInfo;
import com.maxim.hibernate.entity.User;
import com.maxim.hibernate.util.HibernateUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;

public class HebertateRunnerTest {


        @Test
        void localeInfo() {
        try (var sessionFactory = HibernateUtils.buildSessionFactory();
                var session = sessionFactory.openSession()) {
            Transaction t = session.beginTransaction();

            Company company = session.get(Company.class, 14L);
            company.getLocale().add(LocaleInfo.of("ru", "russian desriprion"));
            company.getLocale().add(LocaleInfo.of("en", "english desriprion"));

            t.commit();

        }

        }

        @Test
        void manyToManyTest() {
        try (var sessionFactory = HibernateUtils.buildSessionFactory();
                var session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = session.get(User.class, 1L);

            Chat chat = Chat.builder()
                            .name("superChat4")
                            .build();

            
            user.addChat(chat);
            // session.save(chat);
            session.merge(chat);

            System.out.println();

            session.getTransaction().commit();
                    
            
        }

        }



        @Test
        void checkOneToOne() {
        try (var sessionFactory = HibernateUtils.buildSessionFactory();
                var session = sessionFactory.openSession()) {
            session.beginTransaction();

            User u = session.get(User.class, 1L);

            System.out.println(u);



            // User user = User.builder()
            //                 .username("Louil1.com")
            //                 .build();
            
            // Profile profile = Profile.builder()
            //         .language("en")
            //         .street("DowninaagStrit")
            //         .build();
            // profile.setUser(user);

            // session.merge(user);

            session.getTransaction().commit();

        }
    }

    @Test
    void checkOrphanRemoval() {
        try (var sessionFactory = HibernateUtils.buildSessionFactory();
                var session = sessionFactory.openSession()) {
            session.beginTransaction();

            Company company = session.get(Company.class, 14);
            company.getUsers().removeIf(user -> user.getId() == 9L);

            session.getTransaction().commit();

        }

    }

    @Test
    void checkLazyInitialization() {
        Company company = null;
        try (var sessionFactory = HibernateUtils.buildSessionFactory();
                var session = sessionFactory.openSession()) {
            session.beginTransaction();

            company = session.get(Company.class, 14);

            session.getTransaction().commit();
        }

        var users = company.getUsers();
        System.out.println(users.size());

    }
    @Test
    void getCompanyById() {
        @Cleanup var sessionFactory = HibernateUtils.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        
        Company company = session.get(Company.class, 14);
        Hibernate.initialize(company.getUsers());
        System.out.println();


        session.getTransaction().commit();
    }


 


    @Test
    void deleteCompany() {
        @Cleanup var sessionFactory = HibernateUtils.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        
        Company company = session.get(Company.class, 16);

        session.remove(company);

        session.getTransaction().commit();

    }

    @Test
    void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtils.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        
        Company company = Company.builder()
                            .name("Facebook")
                            .build();

        User user = User.builder()
                        .username("Michel")
                        .build();
        
        company.addUser(user);

        session.merge(company);

        session.getTransaction().commit();
    }

    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        PreparedStatement ps = null;
        ResultSet resultSet = ps.executeQuery();
        resultSet.getString("username");
        resultSet.getString("lastname");

        Class<User> clazz = User.class;
        Constructor<User> constructor = clazz.getConstructor();
        User user = constructor.newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            field.set(user, resultSet.getString("username"));
        }
    }
    
    @Test
    void checkInsertReflectionApi() throws SQLException, IllegalArgumentException, IllegalAccessException {

        User user = User.builder()
                            .username("Poko123")
                            .personalInfo(PersonalInfo.builder()
                                                    .firstname("Poko")
                                                    .lastname("Mizo")
                                                    .birthDate(new Birthday(LocalDate.of(1242,12, 1)))
                                                    .build())
                            .build();

                            
                            String sql = """
                                insert
                                into
                                %s
                                (%s)
                                values
                                (%s)
                                """;
        Class<? extends User> userApi = user.getClass();
        String tableName = Optional.ofNullable(userApi.getAnnotation(Table.class))
                                        .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                                        .orElse(user.getClass().getName());
        Field[] declaredFields = userApi.getDeclaredFields();
        String columnsName = Arrays.stream(declaredFields)
                            .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                                .map(Column::name)
                                .orElse(field.getName()))
                            .collect(Collectors.joining(", "));
        String columnValue = Arrays.stream(declaredFields)
                    .map(field -> "?")
                    .collect(Collectors.joining(", "));
        System.out.println(sql.formatted(tableName, columnsName, columnValue));
        
        Connection connecion = null;
        
        PreparedStatement prepareStatement = connecion.prepareStatement(sql.formatted(tableName, columnsName, columnValue));
        for(Field field : declaredFields) {
            field.setAccessible(true);
            prepareStatement.setObject(1, field.get(user));
        }
    }

    @Test
    void oneToMany() {
        @Cleanup SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Company company = session.get(Company.class, 14);
        assertTrue(company.getId() == 14);

        System.out.println(company);

        session.getTransaction().commit();
    }
}

