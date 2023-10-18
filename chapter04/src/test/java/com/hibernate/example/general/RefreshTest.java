package com.hibernate.example.general;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import com.hibernate.example.model.SimpleObject;
import com.hibernate.example.util.SessionUtil;

public class RefreshTest {
    
    @Test
    public void testRefresh() {
        Long id;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            SimpleObject so = new SimpleObject();

            so.setKey("testMerge");
            so.setValue(1L);

            session.save(so);

            id = so.getId();

            tx.commit();
        }

        SimpleObject obj = ValidateSimpleObject.validate(id, 1L, "testMerge");

        obj.setValue(2L);

        try  (Session session = SessionUtil.getSession()) {
            session.refresh(obj);
        }

        ValidateSimpleObject.validate(id, 1L, "testMerge");
    }
}
