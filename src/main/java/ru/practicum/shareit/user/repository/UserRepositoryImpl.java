package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
public class UserRepositoryImpl implements  UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public UserDto create(UserDto userDto) {
        userDto.setId(id++);
        users.put(userDto.getId(), UserMapper.toUser(userDto));
        return userDto;
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User update(int id, User user) {
        if (user.getName() != null) users.get(id).setName(user.getName());
        if (user.getEmail() != null) users.get(id).setEmail(user.getEmail());
        return users.get(id);
    }

    @Override
    public void delete(int id) {
        users.remove(id);
    }
}
