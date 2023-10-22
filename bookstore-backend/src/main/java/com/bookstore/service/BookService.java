package com.bookstore.service;

import com.bookstore.domain.Book;
import com.bookstore.dto.BookDTO;
import com.bookstore.dto.BookListDTO;
import com.bookstore.repository.BookstoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);

    private BookstoreRepository bookstoreRepository;

    @Autowired
    public BookService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    /**
     * Retrieve all books from database and create the response dto
     * @return books
     */
    public BookListDTO getAllBooks() {
        LOGGER.debug("Start BookService.getAllBooks");
        List<Book> books = bookstoreRepository.findAll();
        if (!books.isEmpty()) {
            LOGGER.debug("Start BookService.getAllBooks");
            return convertToBookListDTO(books);
        }

        LOGGER.warn("0 books found in database");
        return new BookListDTO();
    }

    /**
     * Convert book Entity list into BookListDTO
     * @param books
     * @return bookListDTO
     */
    protected BookListDTO convertToBookListDTO(List<Book> books) {
        List<BookDTO> list = new ArrayList<>();
        books.forEach(book -> list.add(new BookDTO(book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPublicationDate())));
        BookListDTO bookListDTO = new BookListDTO();
        bookListDTO.setBookDTOList(list);
        return bookListDTO;
    }

}
