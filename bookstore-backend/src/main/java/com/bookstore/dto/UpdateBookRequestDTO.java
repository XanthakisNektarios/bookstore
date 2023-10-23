package com.bookstore.dto;

import java.io.Serializable;
import java.util.Date;

public class UpdateBookRequestDTO implements Serializable {

    private Long id;

    private String title;

    private AuthorDTO author;

    private Integer quantity;

    private Date publicationDate;

    public UpdateBookRequestDTO(Long id, String title, AuthorDTO author, Integer quantity, Date publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.publicationDate = publicationDate;
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
        return "UpdateBookRequestDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", quantity=" + quantity +
                ", publicationDate=" + publicationDate +
                '}';
    }
}
