package by.javaguru.mapper;

import by.javaguru.dto.UserCreateDto;
import by.javaguru.entity.User;


public class UserCreateMapper implements Mapper<UserCreateDto, User> {
    private static final UserCreateMapper INSTANCE = new UserCreateMapper();

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .name(object.name())
                .phone(object.phone())
                .email(object.email())
                .password(object.password())
                .role(object.role())
                .gender(object.gender())
                .birthday(object.birthday())
                .build();
    }

    public static UserCreateMapper getInstance() {
        return INSTANCE;
    }
}
