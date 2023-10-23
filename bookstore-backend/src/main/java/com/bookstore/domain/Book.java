package com.bookstore.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

    public Book(String title, Author author, String publisher, int quantity, Date publicationDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.publicationDate = publicationDate;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", publisher='" + publisher + '\'' +
                ", quantity=" + quantity +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
