package com.hibernate.example.naturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class SimpleNaturalEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NaturalId
    Integer badge;

    String name;

    @Column(scale = 2, precision = 5, nullable = false)
    Double royalty;

    public SimpleNaturalEmployee() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRoyalty() {
        return royalty;
    }

    public void setRoyalty(Double royalty) {
        this.royalty = royalty;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((badge == null) ? 0 : badge.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((royalty == null) ? 0 : royalty.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SimpleNaturalEmployee other = (SimpleNaturalEmployee) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (badge == null) {
            if (other.badge != null)
                return false;
        } else if (!badge.equals(other.badge))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (royalty == null) {
            if (other.royalty != null)
                return false;
        } else if (!royalty.equals(other.royalty))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SimpleNaturalEmployee [id=" + id + ", badge=" + badge + ", name=" + name + ", royalty=" + royalty + "]";
    }
    
}
