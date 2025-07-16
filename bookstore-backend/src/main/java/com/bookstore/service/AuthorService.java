package com.bookstore.service;

import com.bookstore.dto.*;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> getAllAuthors();

    AuthorDTO getAuthor(Long id);

    void saveAuthor(AuthorDTO AuthorDTO);

    AuthorDTO updateAuthor(UpdateAuthorRequestDTO updateAuthorRequestDTO);

    void deleteAuthor(AuthorDTO AuthorDTO);
}
