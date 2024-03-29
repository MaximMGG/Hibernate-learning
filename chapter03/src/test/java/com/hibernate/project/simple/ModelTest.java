package com.hibernate.project.simple;

import org.testng.annotations.Test;

import com.hibernate.project.Person;
import com.hibernate.project.Ranking;
import com.hibernate.project.Skill;

public class ModelTest {
    

    @Test
    public void testModelCreation() {
        Person subject = new Person();
        subject.setName("J.C. Smell");
        Person observer = new Person();
        observer.setName("Drew Lombardo");
        Skill skill = new Skill();
        skill.setName("Java");
        Ranking ranking = new Ranking();
        ranking.setSubject(subject);
        ranking.setObserver(observer);
        ranking.setSkill(skill);
        ranking.setRanking(8);
        System.out.println(ranking);
    }
}
