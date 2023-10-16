package com.hibernate.example.broken;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hibernate.example.util.SessionUtil;

public class ProperSimpleInversionTest {
    
        @Test
        public void testProperSimpleInversionCode() {

        Long emailId;
        Long messageId;
        Email email;
        Message message;


        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            email = new Email("Proper");
            message = new Message("Proper");

            email.setMessage(message);
            message.setEmail(email);

            session.save(email);
            session.save(message);

            emailId = email.getId();
            messageId = message.getId();

            tx.commit();

        }
            try(Session session = SessionUtil.getSession()) {
                email = session.get(Email.class, emailId);
                System.out.println(email);
                message = session.get(Message.class, messageId);
                System.out.println(message);
            }

            Assert.assertNotNull(email.getMessage());
            Assert.assertNotNull(message.getContent());
        }
}
