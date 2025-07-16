package com.bookstore.validator;

import com.bookstore.domain.Author;
import com.bookstore.domain.Book;
import com.bookstore.dto.UpdateAuthorRequestDTO;
import com.bookstore.dto.UpdateBookRequestDTO;
import com.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.Optional;

@Component
public class UpdateAuthorRequestDTOValidator implements Validator {


    private AuthorRepository authorRepository;

    @Autowired
    public UpdateAuthorRequestDTOValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateAuthorRequestDTOValidator.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (object == null) {
            errors.reject("Provided UpdateAuthorRequestDTO is null");
            return;
        }
        UpdateAuthorRequestDTO dto = (UpdateAuthorRequestDTO) object;
        performSyntaxValidations(dto, errors);
    }

    protected void performSyntaxValidations(UpdateAuthorRequestDTO dto, Errors errors){
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
    protected void checkNotEmptyMandatoryFields(UpdateAuthorRequestDTO dto, Errors errors) {

        if (dto.firstName() == null) {
            errors.rejectValue("firstName", "mandatory", new String[]{}, "firstName is a mandatory field");
        }

        if (dto.lastName() == null) {
            errors.rejectValue("lastName", "mandatory", new String[]{}, "lastName is a mandatory field");
        }

        if (dto.country() == null) {
            errors.rejectValue("country", "mandatory", new String[]{}, "country is a mandatory field");
        }
    }

    /**
     * check that all fields have valid values
     * @param dto
     * @param errors
     */
    protected void checkFieldsHaveValidValues(UpdateAuthorRequestDTO dto, Errors errors) {
        Optional<Author> author = this.authorRepository.findByFirstNameAndLastName(dto.firstName(), dto.lastName());
        if(author.isEmpty()) {
            errors.rejectValue("author", "author.not.found", new String[]{dto.firstName(), dto.lastName()}, "Author with First Name: {0} and Last Name {1} is not found");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.firstName().length() > 700) {
            errors.rejectValue("firstName", "firstName.exceeds.allowed.length", new String[]{dto.firstName()}, "firstName exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.lastName().length() > 500) {
            errors.rejectValue("lastName", "lastName.exceeds.allowed.length", new String[]{dto.lastName()}, "lastName exceeds allowed character length");
        }

        if(errors.hasErrors()){
            return;
        }

        if (dto.country().length() > 500) {
            errors.rejectValue("country", "country.exceeds.allowed.length", new String[]{dto.country()}, "country exceeds allowed character length");
        }


    }

}
