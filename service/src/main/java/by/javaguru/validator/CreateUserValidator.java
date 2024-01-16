package by.javaguru.validator;

import by.javaguru.dto.UserCreateDto;
import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;
import by.javaguru.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<UserCreateDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(UserCreateDto userDto) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(String.valueOf(userDto.birthday()))) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Gender.find(String.valueOf(userDto.gender())).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Role.find(String.valueOf(userDto.role())).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }

        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
