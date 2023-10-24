package com.hibernate.example.perclass;

import javax.persistence.Entity;

@Entity(name = "PerClassCBook")
public class ComputerBook extends Book{

    String primaryLanguage;

    
}
