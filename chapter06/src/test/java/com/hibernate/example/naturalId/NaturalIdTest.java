package com.hibernate.example.naturalId;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hibernate.example.util.SessionUtil;

public class NaturalIdTest extends IdTestBase {
    

    @Test
    public void testSimpleNaturalId() {
        Integer id = createSimpleEmployee("Molly", 5401).getId();
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SimpleNaturalEmployee employee = session.byId(SimpleNaturalEmployee.class)
                                                    .load(id);
            
            Assert.assertNotNull(employee);
        
            SimpleNaturalEmployee badgedEmployee = session.bySimpleNaturalId(SimpleNaturalEmployee.class)
                                                        .load(5401);
            Assert.assertEquals(employee, badgedEmployee);
            tx.commit();
        }
    }

    @Test
    public void testLoadByNaturalId() {
        Employee initial = createEmployee("MollyJe", 11, 191);
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Employee employee = session.byNaturalId(Employee.class)
                                        .using("section", 11)
                                        .using("department", 191)
                                        .load();

            Assert.assertNotNull(employee);
            Assert.assertEquals(initial, employee);
            
            tx.commit();
        }
    }

    @Test
    public void testLoadById() {
        Integer id = createEmployee("Leo", 10, 289).getId(); 
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Employee boggit = session.byId(Employee.class)
                                    .load(id);
            Assert.assertNotNull(boggit);

            session.delete(boggit);

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Employee em = session.byId(Employee.class).load(id);

            Assert.assertNull(em);
            
            tx.commit();
        }
    }

    @Test
    public void testGetById() {
        Integer id = createEmployee("Leo", 10, 289).getId(); 
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Employee boggit = session.byId(Employee.class)
                                    .getReference(id);
            Assert.assertNotNull(boggit);

            tx.commit();
    }
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            try {
                Employee boggit = session.byId(Employee.class)
                                        .getReference(id);
                boggit.getDepartment();
                
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
            }
            tx.commit();
        }
    }

}
