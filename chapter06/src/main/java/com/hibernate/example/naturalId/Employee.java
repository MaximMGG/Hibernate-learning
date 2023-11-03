package com.hibernate.example.naturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

@Entity
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @NaturalId
    Integer section;

    @NaturalId
    int department;

    String name;

    public Employee(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + section;
        result = prime * result + department;
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
        Employee other = (Employee) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (section != other.section)
            return false;
        if (department != other.department)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", section=" + section + ", department=" + department + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    
}
