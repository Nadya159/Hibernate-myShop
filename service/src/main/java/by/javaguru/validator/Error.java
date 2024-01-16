package by.javaguru.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error extends java.lang.Error {
    String code, message;
}
