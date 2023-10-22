package com.bookstore.web.controller;
import com.bookstore.dto.BookDTO;
import com.bookstore.dto.BookListDTO;
import com.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookstore")
public class BookStoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreController.class);

    private BookService bookService;

    @Autowired
    public BookStoreController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAllBooks")
    ResponseEntity<BookListDTO> getAllBooks() {
        LOGGER.debug("Start BookStoreController.getAllBooks");
        try{
            BookListDTO bookList = bookService.getAllBooks();
            return ResponseEntity.status(HttpStatus.OK).body(bookList);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.getAllBooks. Failed to fetch book list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
