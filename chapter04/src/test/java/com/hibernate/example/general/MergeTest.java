package com.hibernate.example.general;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import com.hibernate.example.model.SimpleObject;
import com.hibernate.example.util.SessionUtil;

public class MergeTest {
    
    @Test
    public void testMerge() {
        Long id;
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SimpleObject simpleObject = new SimpleObject();

            simpleObject.setKey("testMerge");
            simpleObject.setValue(1L);

            session.save(simpleObject);

            id = simpleObject.getId();
            
            tx.commit();
        }

        SimpleObject so = ValidateSimpleObject.validate(id, 1L, "testMerge");

        so.setValue(2L);

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.merge(so);

            tx.commit();
        }

        ValidateSimpleObject.validate(id, 2L, "testMerge");
    }
}
