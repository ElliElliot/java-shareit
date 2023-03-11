package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    List<User> getAll();

    Map<Integer, User> getUsers();

    Optional<User> getById(int id);

    UserDto create(UserDto userDto);

    User update(int id, User user);

    void delete(int id);
}
