package com.bookstore.dto;

import java.io.Serializable;


public record UpdateAuthorRequestDTO(
        String firstName,
        String lastName,
        String country
) implements Serializable {}