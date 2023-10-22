package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.dto.BookListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    public BookService() {
    }

    public BookListDTO getAllBooks() {
        LOGGER.debug("Start BookService.getAllBooks");
        BookDTO bookDTO = new BookDTO("A book", "An Author", 2, new Date());
        List<BookDTO> list = new ArrayList<>();
        list.add(bookDTO);
        BookListDTO books = new BookListDTO();
        books.setBookDTOList(list);
        return books;
    }

}
