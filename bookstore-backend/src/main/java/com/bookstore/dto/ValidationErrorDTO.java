package com.bookstore.dto;

import java.io.Serial;
import java.io.Serializable;

public class ValidationErrorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2924253201035139500L;

    private String field;

    private String message;

    private String defaultMessage;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationErrorDTO(String field, String message, String defaultMessage) {
        this.field = field;
        this.message = message;
        this.defaultMessage = defaultMessage;
    }

    public ValidationErrorDTO(String field, String message) {
        this(field, message, "");
    }

    public ValidationErrorDTO(String message) {
        this("", message);
    }

    public ValidationErrorDTO() {
        this("", "");
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ValidationErrorDTO{");
        sb.append("field='").append(field).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
