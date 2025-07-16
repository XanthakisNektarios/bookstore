package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.dto.UpdateBookRequestDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO getBook(Long id);

    void saveBook(BookDTO bookDTO);

    BookDTO updateBook(UpdateBookRequestDTO updateBookRequestDTO);

    void deleteBook(BookDTO bookDTO);

}
