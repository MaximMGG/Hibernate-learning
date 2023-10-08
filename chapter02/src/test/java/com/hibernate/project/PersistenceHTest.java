package com.hibernate.project.jdbc;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.hibernate.project.pojo.Message;

public class PersistenceHTest {
    
    private SessionFactory factory = null;


    @BeforeClass
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                                            .configure()
                                            .build();
        factory = new MetadataSources(registry)
                            .buildMetadata()
                            .buildSessionFactory();
    }

    public Message saveMessage(String text) {

        Message message = new Message(text);
        try(Session session = factory.openSession()) {
            Transaction tr = session.beginTransaction();
            session.persist(message);
            tr.commit();
        }
        return message;
    }


    @Test
    public void readMessage() {
        Message savedMessage = saveMessage("Hello World!");
        List<Message> list;
        try(Session session = factory.openSession()) {
            list = session.createQuery("from Message", Message.class).list();
        }
        Assert.assertEquals(list.size(), 1);
        for (Message m : list) {
            System.out.println(m);
        }
        Assert.assertEquals(list.get(0), savedMessage);
    }
}
