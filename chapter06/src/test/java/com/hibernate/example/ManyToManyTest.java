package com.hibernate.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hibernate.example.example.Author;
import com.hibernate.example.example.Book;
import com.hibernate.example.util.SessionUtil;

public class ManyToManyTest {
    

    @Test
    public void manyToManytest() {

        Long bookId;
        Long author1Id;
        Long author2Id;

        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Book book = getBook();
            Author[] authors = getAuthors();
            book.addAuthor(authors[0]);
            book.addAuthor(authors[1]);

            authors[0].addBook(book);
            authors[1].addBook(book);

            session.save(authors[0]);
            session.save(authors[1]);
            session.save(book);

            Assert.assertNotNull(book.getId());
            Assert.assertNotNull(authors[0].getId());
            Assert.assertNotNull(authors[1].getId());

            bookId = book.getId();
            author1Id = authors[0].getId();
            author2Id = authors[1].getId();

            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {

            Book book = session.get(Book.class, bookId);
            Author a1 = session.get(Author.class, author1Id);
            Author a2 = session.get(Author.class, author2Id);

            Assert.assertEquals(book.getAuthors().size(), 2);
            Assert.assertEquals(a1.getBooks().size(), 1);
            Assert.assertEquals(a2.getBooks().size(), 1);


        }
    }


    private Book getBook() {
        Book book = new Book();
        book.setName("War and Peace");
        return book;
    }

    private Author[] getAuthors() {
        Author authors[] = new Author[]{new Author(), new Author()};
        authors[0].setName("Pedro");
        authors[1].setName("Hulio");

        return authors;
    }


    @Test
    public void testSimpleManyToMany() {
        Long id;
        try (Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            Book book = getBookSimle();

            session.save(book);

            Assert.assertNotNull(book.getId());

            id = book.getId();
            tx.commit();
        }

        try (Session session = SessionUtil.getSession()) {
            Book b1 = session.load(Book.class, id);

            Assert.assertEquals(b1.getAuthors().size(), 2);
        }
    }


    private Book getBookSimle() {
        Book book = new Book();
        book.setName("Harry Potter");
        Author a1 = new Author();
        a1.setName("Huan");
        Author a2 = new Author();
        a2.setName("Bill");
        a1.addBook(book);
        a2.addBook(book);
        book.addAuthor(a1);
        book.addAuthor(a2);

        return book;
    }

}
