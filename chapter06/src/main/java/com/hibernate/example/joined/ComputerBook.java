package com.hibernate.example.joined;

import javax.persistence.Entity;

@Entity(name = "JoinedCBook")
public class ComputerBook extends Book{
   String primaryLanguage;
}
