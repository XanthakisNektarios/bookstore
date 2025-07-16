package com.bookstore.service.impl;

import com.bookstore.domain.Author;
import com.bookstore.domain.Book;
import com.bookstore.dto.BookDTO;
import com.bookstore.dto.UpdateBookRequestDTO;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;
import com.bookstore.service.ConverterService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private BookRepository bookRepository;

    private AuthorRepository authorRepository;

    private ConverterService converterService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, ConverterService converterService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.converterService = converterService;
    }

    /**
     * Retrieve all books from database and create the response dto
     * @return books
     */
    @Cacheable(value = "books")
    public List<BookDTO> getAllBooks() {
        LOGGER.debug("Start BookServiceImpl.getAllBooks");
        List<Book> books = bookRepository.findAll();
        if (!books.isEmpty()) {
            return converterService.convertToBookListDTO(books);
        }
        return new ArrayList<>();
    }


    /**
     * Retrieve book from database
     * @return book
     */
    public BookDTO getBook(Long id) {
        LOGGER.debug("Start BookServiceImpl.getBook with id: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            LOGGER.warn("Book with id = {} not found", id);
            return null;
        }
        LOGGER.warn("Book with id = {} found in database", id);
        return converterService.convertToBookDTO(book.get());
    }


    /**
     * Save book to database
     */
    @Transactional
    public void saveBook(BookDTO bookDTO) {
        LOGGER.debug("Start BookServiceImpl.saveBook");
        Optional<Author> author = this.authorRepository.findByFirstNameAndLastName(bookDTO.author().firstName(), bookDTO.author().lastName());
        if (author.isEmpty()) {
            LOGGER.warn("Author with name = {} not found", bookDTO.author().firstName() + bookDTO.author().lastName());
        } else {
            this.bookRepository.save(converterService.convertToBookEntity(bookDTO, author.get()));
            LOGGER.warn("Book with title = {} successfully saved in in database", bookDTO.title());
        }

    }

    /**
     * Retrieve book and Update
     * @return book
     */
    @Transactional
    public BookDTO updateBook(UpdateBookRequestDTO updateBookRequestDTO) {
        LOGGER.debug("Start BookServiceImpl.updateBook for book: {}", updateBookRequestDTO);

        Optional<Book> book = bookRepository.findByTitleAndAndPublisher(updateBookRequestDTO.title(), updateBookRequestDTO.publisher());
        if (book.isEmpty()) {
            LOGGER.warn("BookService.updateBook no such book found with title = {}", updateBookRequestDTO.title());
            return null;
        }

        Book foundBook = book.get();
        LOGGER.warn("Book with title = {} found in database ", updateBookRequestDTO.title());
        foundBook.setQuantity(updateBookRequestDTO.quantity());
        this.bookRepository.save(foundBook);

        return converterService.convertToBookDTO(foundBook);
    }

    /**
     * Delete a book
     * @param bookDTO
     */
    @Transactional
    public void deleteBook(BookDTO bookDTO) {
        LOGGER.debug("Start BookServiceImpl.delete for book with title: {}", bookDTO.title());
        bookRepository.deleteByTitleAndAndPublisher(bookDTO.title(), bookDTO.publisher());
    }

}
