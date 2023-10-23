package com.bookstore.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class BookDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 282167793475295631L;

    private String title;

    private AuthorDTO author;

    private String publisher;

    private Integer quantity;

    private Date publicationDate;

    public BookDTO(String title,  AuthorDTO author, String publisher, Integer quantity, Date publicationDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", author=" + author +
                ", quantity=" + quantity +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
