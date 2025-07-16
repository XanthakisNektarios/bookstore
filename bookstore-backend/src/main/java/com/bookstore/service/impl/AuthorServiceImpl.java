package com.bookstore.service.impl;

import com.bookstore.domain.Author;
import com.bookstore.dto.AuthorDTO;
import com.bookstore.dto.UpdateAuthorRequestDTO;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.AuthorService;
import com.bookstore.service.ConverterService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorServiceImpl implements AuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private AuthorRepository authorRepository;

    private ConverterService converterService;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ConverterService converterService) {
        this.authorRepository = authorRepository;
        this.converterService = converterService;
    }

    public List<AuthorDTO> getAllAuthors() {
        LOGGER.debug("Start AuthorServiceImpl.getAllAuthors");
        List<Author> authors = authorRepository.findAll();
        if (!authors.isEmpty()) {
            return converterService.convertToAuthorDTOList(authors);
        }
        return new ArrayList<>();
    }

    /**
     * Retrieve Author from database
     * @return author
     */
    public AuthorDTO getAuthor(Long id) {
        LOGGER.debug("Start AuthorServiceImpl.getAuthor with id: {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isEmpty()) {
            LOGGER.warn("Author with id = {} not found", id);
            return null;
        }
        LOGGER.warn("Author with id = {} found in database", id);
        return converterService.convertToAuthorDTO(author.get());
    }

    /**
     * Save Author to database
     */
    @Transactional
    public void saveAuthor(AuthorDTO authorDTO) {
        LOGGER.debug("Start AuthorServiceImpl.saveAuthor");
        Optional<Author> author = this.authorRepository.findByFirstNameAndLastName(authorDTO.firstName(), authorDTO.lastName());
        if (author.isEmpty()) {
            LOGGER.warn("Author with name = {} not found", authorDTO.firstName() + " " + authorDTO.lastName());
        } else {
            this.authorRepository.save(converterService.convertToAuthorEntity(authorDTO, author.get().getBooks()));
            LOGGER.warn("Author with name = {} successfully saved in in database", authorDTO.firstName() + " " + authorDTO.lastName());
        }
    }

    /**
     * Retrieve Author and Update
     * @param updateAuthorRequestDTO
     * @return author
     */
    @Transactional
    public AuthorDTO updateAuthor(UpdateAuthorRequestDTO updateAuthorRequestDTO) {
        LOGGER.debug("Start AuthorServiceImpl.updateAuthor for author: {}", updateAuthorRequestDTO);

        Optional<Author> author = authorRepository.findByFirstNameAndLastName(updateAuthorRequestDTO.firstName(), updateAuthorRequestDTO.lastName());
        if (author.isEmpty()) {
            LOGGER.warn("AuthorServiceImpl.updateAuthor no such Author found with full name = {}", updateAuthorRequestDTO.firstName() + " " + updateAuthorRequestDTO.lastName());
            return null;
        }

        Author foundAuthor = author.get();
        LOGGER.warn("Author with full name = {} found in database ", updateAuthorRequestDTO.firstName() + " " + updateAuthorRequestDTO.lastName());
        foundAuthor.setFirstName(updateAuthorRequestDTO.firstName());
        foundAuthor.setLastName(updateAuthorRequestDTO.lastName());
        foundAuthor.setCountry(updateAuthorRequestDTO.country());
        this.authorRepository.save(foundAuthor);
        return converterService.convertToAuthorDTO(foundAuthor);
    }



    /**
     * Delete an Author
     * @param authorDTO
     */
    @Transactional
    public void deleteAuthor(AuthorDTO authorDTO) {
        LOGGER.debug("Start AuthorServiceImpl.deleteAuthor for Author: {}", authorDTO.firstName() + " " + authorDTO.lastName());
        try{
            authorRepository.deleteByFirstNameAndLastName(authorDTO.firstName(), authorDTO.lastName());
        } catch (Exception e) {
            LOGGER.warn("Author could not be deleted: {}", e.getMessage());
        }
    }
}
