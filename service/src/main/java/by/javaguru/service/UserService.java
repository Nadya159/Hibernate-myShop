package by.javaguru.service;

import by.javaguru.mapper.UserCreateMapper;
import by.javaguru.mapper.UserReadMapper;
import by.javaguru.dao.UserDao;
import by.javaguru.dto.UserCreateDto;
import by.javaguru.dto.UserReadDto;
import by.javaguru.exception.ValidationException;
import by.javaguru.validator.CreateUserValidator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserReadMapper userReadMapper = UserReadMapper.getInstance();
    private final UserCreateMapper createUserMapper = UserCreateMapper.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public Optional<UserReadDto> findById(Integer id) {
        return userDao.findById(id)
                .map(userReadMapper::mapFrom);
    }

    public boolean delete(Integer id) {
        var maybeUser = userDao.findById(id);
        maybeUser.ifPresent(user -> userDao.delete(user.getId()));
        return maybeUser.isPresent();
    }

    public Integer create(UserCreateDto userCreateDto) {
        var validationResult = createUserValidator.isValid(userCreateDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var userEntity = createUserMapper.mapFrom(userCreateDto);
        return userDao.save(userEntity).getId();
    }

    public Optional<UserReadDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password).map(userReadMapper::mapFrom);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
