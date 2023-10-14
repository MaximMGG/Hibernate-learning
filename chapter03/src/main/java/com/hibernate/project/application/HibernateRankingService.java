package com.hibernate.project.application;


import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hibernate.project.Person;
import com.hibernate.project.Ranking;
import com.hibernate.project.Skill;
import com.hibernate.project.util.SessionUtil;

public class HibernateRankingService implements RankingService {

    //TODO (maxim) test this method
    @Override
    public Person findBestPersonFor(String skill) {
        Person person = null;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            person = findBestPersonFor(session, skill);
            tx.commit();
        }
        return person;
    }

    public Person findBestPersonFor(Session session, String skill) {
        Query<Object[]> query = session.createQuery(
                                        "select r.subject.name, avg(r.ranking) " +
                                        "from Ranking r " +
                                        "where r.skill.name=:skill " +
                                        "gourp by r.subject.name " +
                                        "order by avg(r.ranking) desk", Object[].class);
        query.setParameter("skill", skill);
        query.setMaxResults(1);
        List<Object[]> result = query.list();
        if (result.size() > 0) {
            Object[] row = result.get(0);
            String personName  = row[0].toString();

            return findPerson(session, personName);
        }
        return null;
    }

    @Override
    public Map<String, Integer> findRankingFor(String subject) {
        try (Session session = SessionUtil.getSession()) {

        return findRankingFor(session, subject);
        }
    }

    private Map<String, Integer> findRankingFor(Session session, String subject) {
        Map<String, Integer> result = new HashMap<>();
        Query<Ranking> query = session.createQuery(
                                        "from Ranking r " +
                                        "where r.subject.name=:subject order by r.skill.name", Ranking.class);
        query.setParameter("subject", subject);
        List<Ranking> rankings = query.list();
        String lastSkillName = "";
        int sum = 0;
        int count = 0;

        /**
        query.list().stream().collect(Collectors.toMap(k -> k.getSkill().getName(), v -> v.getRanking()));
        but it do not work becouse we need average ranking for skill, here would be not average, but last
        */

        for(Ranking r : rankings) {
            if (!lastSkillName.equals(r.getSkill().getName())) {
                sum = 0;
                count = 0;
                lastSkillName = r.getSkill().getName();
            }
            sum += r.getRanking();
            count++;
            result.put(lastSkillName, sum / count);
        }
        return result;
    }

    @Override
    public void updateRanking(String subject, String observer, String skill, int rank) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Ranking ranking = findRanking(session, subject, observer, skill);
            if (ranking == null) {
                ranking = new Ranking();
                addRanking(session, subject, observer, skill, rank);
            } else {
                ranking.setRanking(rank);
            }
            tx.commit();
        }
    }


    @Override
    public void addRanking(String subject, String observer, String skill, int rank) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            addRanking(session, subject, observer, skill, rank);

            tx.commit();

        }
    }

    @Override
    public void removeRanking(String subject, String observer, String skill) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Ranking ranking = findRanking(session, subject, observer, skill);
            if (ranking != null) {
                session.delete(ranking);
            }
            tx.commit();
        }
    }

    @Override
    public int getRankingFor(String subject, String skill) {
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            int rank = getRankingFor(session, subject, skill);
            tx.commit();
            return rank;
        }
    }

    private Ranking findRanking(Session session, String subject, String observer, String skill) {
        Query<Ranking> query = session.createQuery(
                                        "from Ranking r " +
                                        "where r.subject.name=:subject and " +
                                        "r.observer.name=:observer and " +
                                        "r.skill.name=:skill", Ranking.class);
        query.setParameter("subject", subject);
        query.setParameter("observer", observer);
        query.setParameter("skill", skill);

        return query.uniqueResult();
    }

    private int getRankingFor(Session session, String subjectName, String skillname) {
        Query<Ranking> query = session.createQuery(
                                        "from Ranking r " +
                                        "where r.subject.name=:subject and " +
                                        "r.skill.name=:skill", Ranking.class);
        query.setParameter("subject", subjectName);
        query.setParameter("skill", skillname);

        IntSummaryStatistics stats = query.list().stream()
                    .collect(Collectors.summarizingInt(Ranking::getRanking));

        return (int) stats.getAverage();
    }

    private void addRanking(Session session, String subjectName, String observerName, String skillName, int rank) {
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

    private Skill saveSkill(Session session, String name) {
        Skill skill = findSkill(session, name);
        if (skill == null) {
            skill = new Skill();
            skill.setName(name);
            session.save(skill);
        }
        return skill;
    }

    private Skill findSkill(Session session, String name) {
        Query<Skill> query = session.createQuery("from Skill s where s.name=:name", Skill.class);
        query.setParameter("name", name);
        return query.uniqueResult();

    }

    private Person savePerson(Session session, String name) {
        Person p = findPerson(session, name);
        if (p == null) {
            p = new Person();
            p.setName(name);
            session.save(p);
        }
        return p;
    }

    private Person findPerson(Session session, String name) {
        Query<Person> query = session.createQuery("from Person p where p.name=:name", Person.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }

}
