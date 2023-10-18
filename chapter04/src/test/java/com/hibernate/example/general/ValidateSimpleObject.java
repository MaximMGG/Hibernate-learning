package com.hibernate.example.general;

import org.hibernate.Session;
import org.testng.Assert;

import com.hibernate.example.model.SimpleObject;
import com.hibernate.example.util.SessionUtil;

public class ValidateSimpleObject {
    
    public static SimpleObject validate(Long id, Long expectedValue, String expectedKey) {
        SimpleObject so = null;
        try (Session session = SessionUtil.getSession()) {
            so = session.load(SimpleObject.class, id);

            Assert.assertEquals(so.getKey(), expectedKey);
            Assert.assertEquals(so.getValue(), expectedValue);
        }
        return so;
    }
}
