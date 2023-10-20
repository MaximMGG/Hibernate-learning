package com.hibernate.example.compoundpk;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ISBN {
    @Column(name = "group_number")
    private int group;
    private int publisher;
    private int title;
    private int checkDigit;

    public ISBN() {}

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(int checkDigit) {
        this.checkDigit = checkDigit;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + group;
        result = prime * result + publisher;
        result = prime * result + title;
        result = prime * result + checkDigit;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ISBN other = (ISBN) obj;
        if (group != other.group) return false;
        if (publisher != other.publisher) return false;
        if (title != other.title) return false;
        if (checkDigit != other.checkDigit) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ISBN [group=" + group + ", publisher=" + publisher + ", title=" + title + ", checkDigit=" + checkDigit
                + "]";
    }
}
