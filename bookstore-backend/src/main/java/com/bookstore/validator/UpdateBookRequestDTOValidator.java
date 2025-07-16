package com.bookstore.validator;

import com.bookstore.domain.Book;
import com.bookstore.dto.UpdateBookRequestDTO;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.Optional;

@Component
public class UpdateBookRequestDTOValidator implements Validator {

    private BookRepository bookRepository;

    @Autowired
    public UpdateBookRequestDTOValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
       return clazz.isAssignableFrom(UpdateBookRequestDTO.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (object == null) {
            errors.reject("Provided UpdateBookRequestDTO is null");
            return;
        }
        UpdateBookRequestDTO dto = (UpdateBookRequestDTO) object;
        performSyntaxValidations(dto, errors);
    }

    protected void performSyntaxValidations(UpdateBookRequestDTO dto, Errors errors){
        checkNotEmptyMandatoryFields(dto, errors);
        if (errors.hasErrors()) {
            return;
        }
        checkFieldsHaveValidValues(dto, errors);
    }

    /**
     * Check that all mandatory fields are not empty
     * @param dto
     * @param errors
     */
    protected void checkNotEmptyMandatoryFields(UpdateBookRequestDTO dto, Errors errors) {

        if (dto.title() == null) {
            errors.rejectValue("title", "mandatory", new String[]{}, "title is a mandatory field");
        }

        if (dto.author() == null) {
            errors.rejectValue("author", "mandatory", new String[]{}, "author is a mandatory field");
        }

        if (dto.quantity() == null) {
            errors.rejectValue("quantity", "mandatory", new String[]{}, "quantity is a mandatory field");
        }

        if (dto.publicationDate() == null) {
            errors.rejectValue("publicationDate", "mandatory", new String[]{}, "publicationDate is a mandatory field");
        }

        if (dto.publisher() == null) {
            errors.rejectValue("publisher", "mandatory", new String[]{}, "publisher is a mandatory field");
        }
    }

    /**
     * check that all fields have valid values
     * @param dto
     * @param errors
     */
    protected void checkFieldsHaveValidValues(UpdateBookRequestDTO dto, Errors errors) {
        Optional<Book> book = this.bookRepository.findByTitleAndAndPublisher(dto.title(), dto.publisher());
        if(book.isEmpty()) {
            errors.rejectValue("title", "book.not.found", new String[]{dto.title(), dto.publisher()}, "book with title: {0} and publisher {1} is not found");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.title().length() > 700) {
            errors.rejectValue("title", "title.exceeds.allowed.length", new String[]{dto.title()}, "title exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.publicationDate().after(new Date())) {
            errors.rejectValue("publicationDate", "publicationDate.is.after.today", new String[]{"" + dto.publicationDate()}, "provided publicationDate {0} cannot precede today {1}".replace("{0}", dto.publicationDate().toString()).replace("{1}", new Date().toString()));
        }

    }

}
