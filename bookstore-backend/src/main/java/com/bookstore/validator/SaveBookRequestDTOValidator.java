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

        if (dto.getTitle() == null) {
            errors.rejectValue("title", "mandatory", new String[]{"" + dto.getTitle()}, "title is a mandatory field");
        }

        if (dto.getAuthor() == null) {
            errors.rejectValue("author", "mandatory", new String[]{"" + dto.getAuthor()}, "author is a mandatory field");
        }

        if (dto.getPublisher() == null) {
            errors.rejectValue("publisher", "mandatory", new String[]{"" + dto.getPublisher()}, "publisher is a mandatory field");
        }

        if (dto.getQuantity() == null) {
            errors.rejectValue("quantity", "mandatory", new String[]{"" + dto.getQuantity()}, "quantity is a mandatory field");
        }

        if (dto.getPublicationDate() == null) {
            errors.rejectValue("publicationDate", "mandatory", new String[]{"" + dto.getPublicationDate()}, "publicationDate is a mandatory field");
        }
    }

    /**
     * check that all fields have valid values
     * @param dto
     * @param errors
     */
    protected void checkFieldsHaveValidValues(BookDTO dto, Errors errors) {

        if (dto.getTitle().length() > 700) {
            errors.rejectValue("title", "title.exceeds.allowed.length", new String[]{"" + dto.getTitle()}, "title exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        checkAuthorHasValidValues(dto.getAuthor(), errors);

        if(errors.hasErrors()){
            return;
        }

        if (dto.getPublisher().length() > 500) {
            errors.rejectValue("publisher", "publisher.exceeds.allowed.length", new String[]{"" + dto.getTitle()}, "publisher exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.getPublicationDate().after(new Date())) {
            errors.rejectValue("publicationDate", "publicationDate.is.after.today", new String[]{"" + dto.getPublicationDate()}, "provided publicationDate {0} cannot precede today {1}".replace("{0}", dto.getPublicationDate().toString()).replace("{1}", new Date().toString()));
        }

    }

    /**
     * check that all fields of AuthorDTO have valid values
     * @param dto
     * @param errors
     */
    protected void checkAuthorHasValidValues(AuthorDTO dto, Errors errors) {

        if (dto.getFirstName().length() > 700) {
            errors.rejectValue("firstName", "firstName.exceeds.allowed.length", new String[]{"" + dto.getFirstName()}, "Author firstName exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.getLastName().length() > 500) {
            errors.rejectValue("lastName", "lastName.exceeds.allowed.length", new String[]{"" + dto.getLastName()}, "Author lastName exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.getCountry().length() > 500) {
            errors.rejectValue("country", "country.exceeds.allowed.length", new String[]{"" + dto.getCountry()}, "Author country exceeds allowed character length");
        }
    }

}
