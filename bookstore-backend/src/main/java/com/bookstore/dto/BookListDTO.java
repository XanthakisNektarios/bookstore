package com.bookstore.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2525142795658507296L;

    private List<BookDTO> bookDTOList = new ArrayList<>();

    public BookListDTO() {
    }

    public BookListDTO(List<BookDTO> bookDTOList) {
        this.bookDTOList = bookDTOList;
    }

    public List<BookDTO> getBookDTOList() {
        return bookDTOList;
    }

    public void setBookDTOList(List<BookDTO> bookDTOList) {
        this.bookDTOList = bookDTOList;
    }

    @Override
    public String toString() {
        return "BookListDTO{" +
                "bookDTOList=" + bookDTOList +
                '}';
    }
}
