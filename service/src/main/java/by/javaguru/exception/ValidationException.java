package by.javaguru.exception;
import lombok.Getter;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    private final List<java.lang.Error> errors;
    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
