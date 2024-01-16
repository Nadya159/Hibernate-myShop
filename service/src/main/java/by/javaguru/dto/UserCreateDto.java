package by.javaguru.dto;

import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;

import java.time.LocalDate;

public record UserCreateDto(
        String name,
        String phone,
        String email,
        Role role,
        Gender gender,
        LocalDate birthday,
        String password) {
}
