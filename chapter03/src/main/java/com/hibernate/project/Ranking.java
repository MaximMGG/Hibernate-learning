package com.hibernate.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @ManyToOne
    private Person subject;
    
    @ManyToOne
    private Person observer;
    
    @ManyToOne
    private Skill skill;
    
    @Column
    private Integer ranking;
    
    public Ranking() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Person getSubject() {
        return subject;
    }

    public void setSubject(Person subject) {
        this.subject = subject;
    }

    public Person getObserver() {
        return observer;
    }

    public void setObserver(Person observer) {
        this.observer = observer;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((observer == null) ? 0 : observer.hashCode());
        result = prime * result + ((skill == null) ? 0 : skill.hashCode());
        result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Ranking)) return false;
        Ranking o = (Ranking) obj;
        return this.observer == o.getObserver() && this.subject == o.getSubject()
                && this.skill == o.getSkill() && this.ranking == o.getRanking() ? true : false;
    }

    @Override
    public String toString() {
        return "Ranking [subject=" + subject + ", observer=" + observer + ", skill=" + skill + ", ranking=" + ranking
                + "]";
    }
     
    
}
