package com.hibernate.example.single;

import javax.persistence.Entity;

@Entity(name = "SingleCBook")
public class ComputerBook extends Book{
    
    String primaryLangugage;
}
