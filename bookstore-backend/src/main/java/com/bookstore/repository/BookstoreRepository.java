package com.bookstore.repository;

import com.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookstoreRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

}
