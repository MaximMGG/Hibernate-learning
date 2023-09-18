package com.maxim.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import com.maxim.hibernate.converter.BirthdayConvertor;
import com.maxim.hibernate.entity.Company;
import com.maxim.hibernate.entity.Profile;
import com.maxim.hibernate.entity.User;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HibernateUtils {

    public static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Company.class);
        configuration.addAnnotatedClass(Profile.class);
        configuration.addAttributeConverter(new BirthdayConvertor());
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        return configuration.buildSessionFactory();
    }
    
}
