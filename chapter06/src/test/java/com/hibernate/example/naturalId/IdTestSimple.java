package com.hibernate.example.naturalId;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hibernate.example.util.SessionUtil;

public class IdTestSimple {
    

    @Test
    public void testSimpleNaturalId() {
        Integer id = createSimpleEmployee("Sophia", 5401).getId();
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            SimpleNaturalEmployee employee = session.byId(SimpleNaturalEmployee.class)
                                                    .load(5401);
            
            Assert.assertNotNull(employee);

            SimpleNaturalEmployee badgedEmployee = session.bySimpleNaturalId(SimpleNaturalEmployee.class)
                                                            .load(5401);
            Assert.assertEquals(employee, badgedEmployee);
            tx.commit();
        }
    }

    private Employee createSimpleEmployee(String string, int i) {
        Employee em = new Employee();
        return em;
    }


}
