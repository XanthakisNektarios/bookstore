package com.bookstore.service;

import com.bookstore.domain.Book;
import com.bookstore.dto.BookDTO;
import com.bookstore.dto.BookListDTO;
import com.bookstore.dto.UpdateBookRequestDTO;
import com.bookstore.repository.BookstoreRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * Retrieve book from database and create the response dto
     * @return books
     */
    public BookDTO getBook(Long id) {
        LOGGER.debug("Start BookService.getBook with id: " + id);
        Optional<Book> book = bookstoreRepository.findById(id);
        if (book.isEmpty()) {
            LOGGER.warn("Book with title = {title} not found".replace("{id}", "" + id));
            return null;
        }
        LOGGER.warn("Book with title = {id} found in database".replace("{id}", "" + id));
        return convertToBookDTO(book.get());
    }

    /**
     * Retrieve book and Update
     * @return books
     */
    @Transactional
    public BookDTO updateBook(UpdateBookRequestDTO updateBookRequestDTO) {
        LOGGER.debug("Start BookService.updateBook for book: " + updateBookRequestDTO);

        Optional<Book> book = bookstoreRepository.findById(updateBookRequestDTO.getId());
        if (book.isEmpty()) {
            LOGGER.warn("BookService.updateBook no such book found with title = {title}".replace("{title}", "" + updateBookRequestDTO.getTitle()));
            return null;
        }

        Book foundBook = book.get();
        LOGGER.warn("Book with title = {title} found in database and updated".replace("{title}", "" + updateBookRequestDTO.getTitle()));
        foundBook.setAuthor(updateBookRequestDTO.getAuthor());
        foundBook.setTitle(updateBookRequestDTO.getTitle());
        foundBook.setQuantity(updateBookRequestDTO.getQuantity());
        foundBook.setPublicationDate(updateBookRequestDTO.getPublicationDate());
        this.bookstoreRepository.save(foundBook);

        return convertToBookDTO(foundBook);
    }

    /**
     * Delete a book
     * @param id
     */
    @Transactional
    public void deleteBook(Long id) {
        LOGGER.trace("Enter deleteNotification. notificationId = {}", id);
        bookstoreRepository.deleteById(id);
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

    /**
     * Convert book Entity into BookDTO
     * @param book
     * @return bookListDTO
     */
    protected BookDTO convertToBookDTO(Book book) {
        return new BookDTO(book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPublicationDate());
    }

}
