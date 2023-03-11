package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.exception.ValidateException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserMapper userMapper = new UserMapper();

    @Override
    public UserDto create(UserDto userDto) {
        validate(userMapper.toUser(userDto));
        log.info("Создан пользователь с id {}", userDto.getId());
        return userRepository.create(userDto);
    }

    @Override
    public List<User> getAll() {
        log.info("Отправлен список пользователей");
        return userRepository.getAll();
    }

    @Override
    public Optional<User> getById(int id) {
        if (!userRepository.getUsers().containsKey(id)) {
            log.warn("Пользователь с id {} не найден", id);
            throw new ObjectNotFoundException("Пользоаватель не найден");
        }
        log.info("Отправлен пользователь с id {}", id);
        return userRepository.getById(id);
    }

    @Override
    public UserDto update(int id, User user) {
        if (user.getEmail() != null) {
            validate(user);
        }
        if (userRepository.getUsers().containsKey(id)) {
            return userRepository.update(id, user);
        } else {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public void delete(int id) {
        log.info("Пользователь с id {} удалён", id);
        userRepository.delete(id);
    }

    private void validate (User user) {
        List<User> users = userRepository.getAll();
        boolean emailValidate = users.stream()
                .anyMatch(repoUser -> repoUser.getEmail().equals(user.getEmail()));
        if (emailValidate) {
            log.warn("Пользователь с таким e-mail уже существует");
            throw new ValidateException("Пользователь с таким e-mail уже существует");
        }
    }
}
