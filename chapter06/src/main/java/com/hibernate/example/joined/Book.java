package com.hibernate.example.joined;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity(name = "JoinedBook")
@Inheritance(strategy = InheritanceType.JOINED)
public class Book {

    @Id
    Long bookId;
    String title;
    
}
