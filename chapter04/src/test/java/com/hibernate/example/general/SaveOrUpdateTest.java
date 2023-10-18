package com.hibernate.example.general;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hibernate.example.model.SimpleObject;
import com.hibernate.example.util.SessionUtil;

public class SaveOrUpdateTest {
    

    @Test
    public void testSaveOrUpdateEntity() {
        Long id;
        SimpleObject obj;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery("delete from SimpleObject")
                    .executeUpdate();
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj = new SimpleObject();
            obj.setKey("Open Source and Standards");
            obj.setValue(14L);

            session.save(obj);

            Assert.assertNotNull(obj.getId());
            
            id = obj.getId();
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            obj.setValue(12L);
            session.saveOrUpdate(obj);

            tx.commit();
        }

        Assert.assertEquals(id, obj.getId());

        try (Session session = SessionUtil.getSession()) {
            List<SimpleObject> objects = session
                                        .createQuery("from SimpleObject", SimpleObject.class)
                                        .list();
            Assert.assertEquals(1, objects.size());
        }


    }
}
