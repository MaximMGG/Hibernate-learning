package com.hibernate.example.compoundpk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(IdClassBook.EmbeddedISBN.class)
public class IdClassBook {
    @Id
    @Column(name = "group_number")
    private int group;

    @Id
    private int publisher;

    @Id
    private int title;

    @Id
    private int checkdigit;

    public IdClassBook() {}

    static class EmbeddedISBN implements Serializable {
        int group;
        int publisher;
        int title;
        int checkdigit;

        public EmbeddedISBN() {}

        // source matches the listing for ISBN.java
    }
    
}
