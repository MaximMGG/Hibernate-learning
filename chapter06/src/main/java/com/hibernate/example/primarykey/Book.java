package com.hibernate.example.primarykey;

public class Book {
    private String title;

    private int pages;

    private int id;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + pages;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Book)) return false;
        Book o = (Book) obj;
        return title.equals(o.getTitle()) && pages == o.getPages() && id == o.getId();
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", pages=" + pages + ", id=" + id + "]";
    }
}
