package com.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthorDTO(
        String firstName,
        String lastName,
        String country,
        List<BookDTO> books
) implements Serializable {
    @Serial
    private static final long serialVersionUID = -4841788423028490106L;
}
