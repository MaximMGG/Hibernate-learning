package com.hibernate.project.util;

import org.hibernate.Session;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SessionBuilderTest {
    @Test
    public void testSessionFacotry() {
        try (Session session = SessionUtil.getSession()) {
            Assert.assertNotNull(session);
        }
    }
}
