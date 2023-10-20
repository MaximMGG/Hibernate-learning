package com.hibernate.example.compoundpk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class EmbeddedPKBook {

    @EmbeddedId
    private EmbeddedISBN id;

    @Column
    private String name;


    public EmbeddedPKBook() {}

    static class EmbeddedISBN implements Serializable {
        // source matches the listing for JSBN.java
    }
    
}
