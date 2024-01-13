package by.javaguru.dto;

import by.javaguru.entity.Gender;
import by.javaguru.entity.Role;

import java.time.LocalDate;

public record UserReadDto(
        Integer id,
        String name,
        String email,
        String phone,
        Role role,
        Gender gender,
        LocalDate birthday) {
}
