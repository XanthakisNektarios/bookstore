package com.bookstore.service.impl;

import com.bookstore.domain.Author;
import com.bookstore.domain.Book;
import com.bookstore.dto.AuthorDTO;
import com.bookstore.dto.BookDTO;
import com.bookstore.service.ConverterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConverterServiceImpl implements ConverterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConverterServiceImpl.class);


    /**
     * Convert Book Entity list into BookListDTO
     * @param books
     * @return list
     */
    public List<BookDTO> convertToBookListDTO(List<Book> books) {
        List<BookDTO> list = new ArrayList<>();
        books.forEach(book -> list.add(convertToBookDTO(book)));
        return list;
    }

    /**
     * Convert Book Entity into BookDTO
     * @param book
     * @return BookDTO
     */
    public BookDTO convertToBookDTO(Book book) {
        return new BookDTO(
            book.getTitle(),
            new AuthorDTO(
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName(),
                book.getAuthor().getCountry(),
                null
            ),
            book.getPublisher(),
            book.getQuantity(),
            book.getPublicationDate()
        );
    }

    /**
     * Convert BookDTO into Book Entity
     * @param bookDTO
     * @param author
     * @return book
     */
    public Book convertToBookEntity(BookDTO bookDTO, Author author) {
        return new Book(
            bookDTO.title(),
            author,
            bookDTO.publisher(),
            bookDTO.quantity(),
            bookDTO.publicationDate()
        );
    }


    /**
     * Convert author Entity list into List<AuthorDTO>
     * @param authors
     * @return list
     */
    public List<AuthorDTO> convertToAuthorDTOList(List<Author> authors) {
        List<AuthorDTO> list = new ArrayList<>();
        authors.forEach(author -> list.add(convertToAuthorDTO(author)));
        return list;
    }

    /**
     * Convert Author Entity into AuthorDTO
     * @param author
     * @return AuthorDTO
     */
    public AuthorDTO convertToAuthorDTO(Author author) {
        return new AuthorDTO(
            author.getFirstName(),
            author.getLastName(),
            author.getCountry(),
            author.getBooks().stream().map(
                bookEntity -> new BookDTO(
                    bookEntity.getTitle(),
                    null,
                    bookEntity.getPublisher(),
                    bookEntity.getQuantity(),
                    bookEntity.getPublicationDate()
                )
            ).toList()
        );
    }

    /**
     * Convert AuthorDTO into Author Entity
     * @param authorDTO
     * @param books
     * @return author
     */
    public Author convertToAuthorEntity(AuthorDTO authorDTO, List<Book> books) {
        return new Author(
            authorDTO.firstName(),
            authorDTO.lastName(),
            authorDTO.country(),
            books
        );
    }
}
