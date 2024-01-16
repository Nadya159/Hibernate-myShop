package by.javaguru.mapper;

import by.javaguru.dto.UserReadDto;
import by.javaguru.entity.User;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto>{
    private static final UserReadMapper INSTANCE = new UserReadMapper();
    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getName(),
                object.getEmail(),
                object.getPhone(),
                object.getRole(),
                object.getGender(),
                object.getBirthday()
        );
    }
    public static UserReadMapper getInstance() {
        return INSTANCE;
    }
}
