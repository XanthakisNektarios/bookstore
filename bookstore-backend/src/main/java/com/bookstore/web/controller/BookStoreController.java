package com.bookstore.web.controller;

import com.bookstore.dto.*;
import com.bookstore.service.AuthorService;
import com.bookstore.service.BookService;
import com.bookstore.validator.SaveBookRequestDTOValidator;
import com.bookstore.validator.UpdateAuthorRequestDTOValidator;
import com.bookstore.validator.UpdateBookRequestDTOValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookstore")
public class BookStoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreController.class);

    private BookService bookService;

    private AuthorService authorService;

    private UpdateBookRequestDTOValidator updateBookRequestDTOValidator;

    private SaveBookRequestDTOValidator saveBookRequestDTOValidator;

    private UpdateAuthorRequestDTOValidator updateAuthorRequestDTOValidator;

    @Autowired
    public BookStoreController(BookService bookService,
                               AuthorService authorService,
                               UpdateBookRequestDTOValidator updateBookRequestDTOValidator,
                               SaveBookRequestDTOValidator saveBookRequestDTOValidator,
                               UpdateAuthorRequestDTOValidator updateAuthorRequestDTOValidator) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.updateBookRequestDTOValidator = updateBookRequestDTOValidator;
        this.saveBookRequestDTOValidator = saveBookRequestDTOValidator;
        this.updateAuthorRequestDTOValidator = updateAuthorRequestDTOValidator;
    }

    /**
     * Get all existing books
     * @return
     */
    @GetMapping(value="/getAllBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all Books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all Books ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    ResponseEntity<List<BookDTO>> getAllBooks() {
        LOGGER.debug("Start BookStoreController.getAllBooks");
        try{
            List<BookDTO> response = bookService.getAllBooks();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.getAllBooks. Failed to fetch Book list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get book
     * @param id
     * @return
     */
    @GetMapping(value="/getBook/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Book ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content )
    })
    ResponseEntity<BookDTO> getBook(@PathVariable("id") Long id) {
        LOGGER.debug("Start BookStoreController.getBook");
        try{
            BookDTO response = bookService.getBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.getBook. Failed to fetch Book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Save book
     * @param bookDTO
     * @return
     */
    @PostMapping(value="/addBook")
    @Operation(summary = "Save a Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved a Book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Preconditions Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("Start BookStoreController.addBook");
        try{
            BindingResult errors = new BindException(bookDTO, "bookDTO");
            this.saveBookRequestDTOValidator.validate(bookDTO, errors);
            if (errors.hasErrors()) {
                List<ValidationErrorDTO> errorMessages = errors.getFieldErrors().stream().map(error -> new ValidationErrorDTO(error.getField(), error.getCode(), error.getDefaultMessage())).collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorMessages);
            }
            bookService.saveBook(bookDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully saved Book");
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.addBook. Failed to save Book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Update book
     * @param updateBookRequestDTO
     * @return
     */
    @PutMapping(value="/updateBook", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated",
                    content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Precondition Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> updateBook(@RequestBody UpdateBookRequestDTO updateBookRequestDTO) {
        LOGGER.debug("Start BookStoreController.updateBook");
        try{
            BindingResult errors = new BindException(updateBookRequestDTO, "updateBookRequestDTO");
            this.updateBookRequestDTOValidator.validate(updateBookRequestDTO, errors);
            if (errors.hasErrors()) {
                List<ValidationErrorDTO> errorMessages = errors.getFieldErrors().stream().map(error -> new ValidationErrorDTO(error.getField(), error.getCode(), error.getDefaultMessage())).collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorMessages);
            }
            BookDTO response = bookService.updateBook(updateBookRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.updateBook. Failed to update Book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Delete a Book
     * @param bookDTO
     * @return
     */
    @DeleteMapping(value="/deleteBook", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete a Book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a Book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Precondition Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> deleteBook(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("Start BookStoreController.deleteBook");
        try{
            bookService.deleteBook(bookDTO);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.deleteBook. Failed to delete Book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get all existing authors
     * @return
     */
    @GetMapping(value="/getAllAuthors", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all Authors ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        LOGGER.debug("Start BookStoreController.getAllAuthors");
        try{
            List<AuthorDTO> response = authorService.getAllAuthors();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.getAllAuthors. Failed to fetch Author list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Get Author
     * @param id
     * @return
     */
    @GetMapping(value="/getAuthor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get an Author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched Author ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content )
    })
    ResponseEntity<AuthorDTO> getAuthor(@PathVariable("id") Long id) {
        LOGGER.debug("Start BookStoreController.getAuthor");
        try{
            AuthorDTO response = authorService.getAuthor(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.getAuthor. Failed to fetch Author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Save Author
     * @param authorDTO
     * @return
     */
    @PostMapping(value="/addAuthor")
    @Operation(summary = "Save an Author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved an Author",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Preconditions Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> addAuthor(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("Start BookStoreController.addAuthor");
        try{
//            BindingResult errors = new BindException(authorDTO, "authorDTO");
//            this.saveBookRequestDTOValidator.validate(authorDTO, errors);
//            if (errors.hasErrors()) {
//                List<ValidationErrorDTO> errorMessages = errors.getFieldErrors().stream().map(error -> new ValidationErrorDTO(error.getField(), error.getCode(), error.getDefaultMessage())).collect(Collectors.toList());
//                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorMessages);
//            }
            authorService.saveAuthor(authorDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully saved Author");
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.addBook. Failed to save Author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /**
     * Update Author
     * @param updateAuthorRequestDTO
     * @return
     */
    @PutMapping(value="/updateAuthor", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an Author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Precondition Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> updateAuthor(@RequestBody UpdateAuthorRequestDTO updateAuthorRequestDTO) {
        LOGGER.debug("Start BookStoreController.updateAuthor");
        try{
            BindingResult errors = new BindException(updateAuthorRequestDTO, "updateAuthorRequestDTO");
            this.updateAuthorRequestDTOValidator.validate(updateAuthorRequestDTO, errors);
            if (errors.hasErrors()) {
                List<ValidationErrorDTO> errorMessages = errors.getFieldErrors().stream().map(error -> new ValidationErrorDTO(error.getField(), error.getCode(), error.getDefaultMessage())).collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorMessages);
            }
            AuthorDTO response = authorService.updateAuthor(updateAuthorRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.updateAuthor. Failed to update Author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Delete an Author
     * @param authorDTO
     * @return
     */
    @DeleteMapping(value="/deleteAuthor", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete an Author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete an Author",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Precondition Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> deleteAuthor(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("Start BookStoreController.deleteAuthor");
        try{
            authorService.deleteAuthor(authorDTO);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.deleteAuthor. Failed to delete Author", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
