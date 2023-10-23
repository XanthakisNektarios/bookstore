package com.bookstore.repository;

import com.bookstore.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository  extends JpaRepository<Author, Long> {

    Optional<Author> findByFirstNameAndLastName(String firstname, String lastName);

}
