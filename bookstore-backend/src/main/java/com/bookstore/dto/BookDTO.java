package com.bookstore.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class BookDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 282167793475295631L;

    private String name;

    private String author;

    private Integer quantity;

    private Date yearOfPublication;

    public BookDTO(String name, String author, Integer quantity, Date yearOfPublication) {
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.yearOfPublication = yearOfPublication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Date yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
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
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", quantity='" + quantity + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
}
