package com.bookstore.validator;

import com.bookstore.domain.Book;
import com.bookstore.dto.UpdateBookRequestDTO;
import com.bookstore.repository.BookstoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.Optional;

@Component
public class UpdateBookRequestDTOValidator implements Validator {

    private BookstoreRepository bookstoreRepository;

    @Autowired
    public UpdateBookRequestDTOValidator(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
       return clazz.isAssignableFrom(UpdateBookRequestDTO.class);
    }

    @Override
    public void validate(Object object, Errors errors) {
        if (object == null) {
            errors.reject("Provided NotificationManagementDTOValidator is null");
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
        if (dto.getId() == null) {
            errors.rejectValue("id", "mandatory", new String[]{"" + dto.getId()}, "id is a mandatory field");
        }

        if (dto.getTitle() == null) {
            errors.rejectValue("title", "mandatory", new String[]{"" + dto.getTitle()}, "title is a mandatory field");
        }

        if (dto.getAuthor() == null) {
            errors.rejectValue("author", "mandatory", new String[]{"" + dto.getAuthor()}, "author is a mandatory field");
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
    protected void checkFieldsHaveValidValues(UpdateBookRequestDTO dto, Errors errors) {
        Optional<Book> book = this.bookstoreRepository.findById(dto.getId());
        if(book.isEmpty()) {
            errors.rejectValue("id", "id.not.found", new String[]{"" + dto.getId()}, "book with id: {0} is not found".replace("{0}", dto.getId().toString()));
        }
        if (dto.getId() == null) {

        }
        if(errors.hasErrors()){
            return;
        }
        if (dto.getTitle().length() > 700) {
            errors.rejectValue("title", "title.exceeds.allowed.length", new String[]{"" + dto.getTitle()}, "title exceeds allowed character length");
        }
        if(errors.hasErrors()){
            return;
        }
        if (dto.getAuthor().length() > 500) {
            errors.rejectValue("author", "author.exceeds.allowed.length", new String[]{"" + dto.getAuthor()}, "author exceeds allowed character length");
        }
        if(errors.hasErrors()){
            return;
        }
        if (dto.getPublicationDate().after(new Date())) {
            errors.rejectValue("publicationDate", "publicationDate.is.after.today", new String[]{"" + dto.getPublicationDate()}, "provided publicationDate {0} cannot precede today {1}".replace("{0}", dto.getPublicationDate().toString()).replace("{1}", new Date().toString()));
        }
    }

}
