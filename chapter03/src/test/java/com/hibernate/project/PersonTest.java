package com.hibernate.project;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PersonTest {
    
    private SessionFactory factory;

    @BeforeClass
    public void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                                                    .configure()
                                                    .build();
        factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testSavePerson() {
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Person person = new Person();
            person.setName("M. Hrunenko");
            session.save(person);

            tx.commit();
        }
    }

    
    @Test
    public void testSaveRanking() {
        try(Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Person subject = savePerson(session, "J. C. Small");
            Person observer = savePerson(session, "Drew Lombardo");
            Skill skill = saveSkill(session, "Java");
            
            Ranking ranking = new Ranking();
            ranking.setSubject(subject);
            ranking.setObserver(observer);
            ranking.setSkill(skill);
            ranking.setRanking(20);
            session.save(ranking);
            tx.commit();
        }
    }

    private Skill findSkill(Session session, String name) {
        Query<Skill> query = session.createQuery("from Skill s where s.name=:name", Skill.class);
        query.setParameter("name", name);
        Skill skill = query.uniqueResult();
        return skill;
    }

    private Skill saveSkill(Session session, String name) {
        Skill skill = findSkill(session, name);
        if (skill == null) {
            skill = new Skill();
            skill.setName(name);
            session.save(skill);
        }
        return skill;
    }

    private Person findPerson(Session session, String name) {
        Query<Person> query = session.createQuery("from Person p where p.name=:name", Person.class);
        query.setParameter("name", name);
        Person person = query.uniqueResult();
        return person;
    }
    
    private Person savePerson(Session session, String name) {
        Person person = findPerson(session, name);
        if(person == null) {
            person = new Person();
            person.setName(name);
            session.save(person);
        }
        return person;
    }

    @Test
    public void testRankings() {
        populateRankingData();
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Query<Ranking> query = session.createQuery("from Ranking r "
                                            + "where r.subject.name=:name "
                                            + "and r.skill.name=:skill", Ranking.class);
            query.setParameter("name", "J. C. Smell");
            query.setParameter("skill", "Java");

            IntSummaryStatistics stats = query.list()
                                                .stream()
                                                .collect(Collectors.summarizingInt(Ranking::getRanking));

            long count = stats.getCount();
            int average = (int) stats.getAverage();
            
            tx.commit();

            session.close();
            Assert.assertEquals(count, 3);
            Assert.assertEquals(average, 7);
        }
    }


    private void populateRankingData() {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            createDate(session, "J. C. Smell", "Gene Showrama", "Java", 6);
            createDate(session, "J. C. Smell", "Scottaball Most", "Java", 7);
            createDate(session, "J. C. Smell", "Drew Lombardo", "Java", 8);

            tx.commit();
        }
    }


    private void createDate(Session session, String subjectName, String observerName, String skillName, int rank) {
        Person subject = savePerson(session, subjectName);
        Person observer = savePerson(session, observerName);
        Skill skill = saveSkill(session, skillName);

        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(rank);

        session.save(ranking);
    }
}
