package by.javaguru.dto;

import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserCreateDto(
        @NotEmpty(message = "{name_invalid}")
        String name,
        @NotEmpty(message = "{phone_invalid}")
        @Size(min=11, max=11, message = "{phone_invalid}")
        String phone,
        @Email String email,
        Role role,
        Gender gender,
        LocalDate birthday,
        @NotEmpty(message = "{pswd_invalid}")
        String password) {
}
