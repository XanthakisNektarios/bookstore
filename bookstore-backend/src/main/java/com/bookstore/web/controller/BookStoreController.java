package com.bookstore.web.controller;

import com.bookstore.dto.BookDTO;
import com.bookstore.dto.BookListDTO;
import com.bookstore.dto.UpdateBookRequestDTO;
import com.bookstore.dto.ValidationErrorDTO;
import com.bookstore.service.BookService;
import com.bookstore.validator.SaveBookRequestDTOValidator;
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

    private UpdateBookRequestDTOValidator updateBookRequestDTOValidator;

    private SaveBookRequestDTOValidator saveBookRequestDTOValidator;

    @Autowired
    public BookStoreController(BookService bookService,
                               UpdateBookRequestDTOValidator updateBookRequestDTOValidator,
                               SaveBookRequestDTOValidator saveBookRequestDTOValidator) {
        this.bookService = bookService;
        this.updateBookRequestDTOValidator = updateBookRequestDTOValidator;
        this.saveBookRequestDTOValidator = saveBookRequestDTOValidator;
    }

    /**
     * Get all existing books
     * @return
     */
    @GetMapping(value="/getAllBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all book ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookListDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)})
    ResponseEntity<BookListDTO> getAllBooks() {
        LOGGER.debug("Start BookStoreController.getAllBooks");
        try{
            BookListDTO response = bookService.getAllBooks();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.getAllBooks. Failed to fetch book list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Get book
     * @param id
     * @return
     */
    @GetMapping(value="/getBook/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched book ",
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
            LOGGER.error("End BookStoreController.getBook. Failed to fetch book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Save book
     * @param bookDTO
     * @return
     */
    @PostMapping(value="/addBook")
    @Operation(summary = "Save a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all book ",
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
            LOGGER.error("End BookStoreController.addBook. Failed to fetch book", e);
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
            LOGGER.error("End BookStoreController.updateBook. Failed to update book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Delete a book
     * @param id
     * @return
     */
    @DeleteMapping(value="/deleteBook/{id}")
    @Operation(summary = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "412", description = "Precondition Failed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content)
    })
    ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        LOGGER.debug("Start BookStoreController.deleteBook");
        try{
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Throwable e){
            LOGGER.error("End BookStoreController.deleteBook. Failed to delete book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
