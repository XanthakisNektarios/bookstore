package com.bookstore.service;


import com.bookstore.domain.Author;
import com.bookstore.domain.Book;
import com.bookstore.dto.AuthorDTO;
import com.bookstore.dto.BookDTO;

import java.util.List;

public interface ConverterService {

    List<BookDTO> convertToBookListDTO(List<Book> books);

    BookDTO convertToBookDTO(Book book);

    Book convertToBookEntity(BookDTO bookDTO, Author author);

    List<AuthorDTO> convertToAuthorDTOList(List<Author> authors);

    AuthorDTO convertToAuthorDTO(Author author);

    Author convertToAuthorEntity(AuthorDTO authorDTO, List<Book> books);
}
