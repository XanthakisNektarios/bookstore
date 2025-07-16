package com.bookstore.validator;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.dto.BookDTO;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class SaveBookRequestDTOValidator implements Validator {

    private BookRepository bookRepository;

    @Autowired
    public SaveBookRequestDTOValidator(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(BookDTO.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (object == null) {
            errors.reject("Provided BookDTO is null");
            return;
        }
        BookDTO dto = (BookDTO) object;
        performSyntaxValidations(dto, errors);
    }

    protected void performSyntaxValidations(BookDTO dto, Errors errors){
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
    protected void checkNotEmptyMandatoryFields(BookDTO dto, Errors errors) {

        if (dto.title() == null) {
            errors.rejectValue("title", "mandatory", new String[]{""}, "title is a mandatory field");
        }

        if (dto.author() == null) {
            errors.rejectValue("author", "mandatory", new String[]{""}, "author is a mandatory field");
        }

        if (dto.publisher() == null) {
            errors.rejectValue("publisher", "mandatory", new String[]{""}, "publisher is a mandatory field");
        }

        if (dto.quantity() == null) {
            errors.rejectValue("quantity", "mandatory", new String[]{""}, "quantity is a mandatory field");
        }

        if (dto.publicationDate() == null) {
            errors.rejectValue("publicationDate", "mandatory", new String[]{""}, "publicationDate is a mandatory field");
        }
    }

    /**
     * check that all fields have valid values
     * @param dto
     * @param errors
     */
    protected void checkFieldsHaveValidValues(BookDTO dto, Errors errors) {

        if (dto.title().length() > 700) {
            errors.rejectValue("title", "title.exceeds.allowed.length", new String[]{dto.title()}, "title exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        checkAuthorHasValidValues(dto.author(), errors);

        if(errors.hasErrors()){
            return;
        }

        if (dto.publisher().length() > 500) {
            errors.rejectValue("publisher", "publisher.exceeds.allowed.length", new String[]{dto.publisher()}, "publisher exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.publicationDate().after(new Date())) {
            errors.rejectValue("publicationDate", "publicationDate.is.after.today", new String[]{"" + dto.publicationDate()}, "provided publicationDate {0} cannot precede today {1}".replace("{0}", dto.publicationDate().toString()).replace("{1}", new Date().toString()));
        }

    }

    /**
     * check that all fields of AuthorDTO have valid values
     * @param dto
     * @param errors
     */
    protected void checkAuthorHasValidValues(AuthorDTO dto, Errors errors) {

        if (dto.firstName().length() > 700) {
            errors.rejectValue("firstName", "firstName.exceeds.allowed.length", new String[]{dto.firstName()}, "Author firstName exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.lastName().length() > 500) {
            errors.rejectValue("lastName", "lastName.exceeds.allowed.length", new String[]{dto.lastName()}, "Author lastName exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.country().length() > 500) {
            errors.rejectValue("country", "country.exceeds.allowed.length", new String[]{dto.country()}, "Author country exceeds allowed character length");
        }
    }

}
