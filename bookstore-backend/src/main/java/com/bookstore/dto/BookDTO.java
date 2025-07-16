package com.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDTO(
        String title,
        AuthorDTO author,
        String publisher,
        Integer quantity,
        Date publicationDate
) implements Serializable {}