package com.bookstore.dto;

import java.io.Serializable;
import java.util.Date;

public record UpdateBookRequestDTO(
        String title,
        AuthorDTO author,
        Integer quantity,
        Date publicationDate,
        String publisher
) implements Serializable {}
