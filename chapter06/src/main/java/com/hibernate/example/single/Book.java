package com.hibernate.example.single;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "SingleBook")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Book {

    @Id
    private Long id;

    String title;
    
}
