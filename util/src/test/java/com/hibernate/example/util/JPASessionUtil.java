package com.hibernate.example.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class JPASessionUtil {
    
    private static Map<String, EntityManagerFactory> persistenceUtils = new HashMap<>();

    @SuppressWarnings("WeakerAccess")
    public static synchronized EntityManager getEntityManager(String persistenceUnitName) {
        persistenceUtils.putIfAbsent(persistenceUnitName, 
                    Persistence.createEntityManagerFactory(persistenceUnitName));
        

        return persistenceUtils.get(persistenceUnitName).createEntityManager();
    }

    public static Session getSession(String persistenceUnitName) {
        return getEntityManager(persistenceUnitName).unwrap(Session.class);
    }
}
