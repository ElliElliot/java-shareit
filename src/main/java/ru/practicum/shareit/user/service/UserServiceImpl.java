package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.AlreadyUsedEmail;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = convertDtoToUser(userDto);
        log.info("Создан пользователь с id {}", userDto.getId());
        return convertUserToDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAll() {
        log.info("Отправлен список пользователей");
        return userRepository.findAll().stream()
                .map(this::convertUserToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) {
        log.info("Отправлен пользователь с id {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Не найден пользователь с id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserDtoById(long userId) {
        return convertUserToDto(getUserById(userId));
    }

    @Override
    public UserDto update(long id, UserDto userDto) {
        isExistUser(id);
        User user = userRepository.findById(id).get();
        if (userRepository.findByEmail(user.getEmail()).getId() != id) throw new AlreadyUsedEmail(user.getEmail());
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        return convertUserToDto(userRepository.save(user));
    }

    @Override
    public void delete(long id) {
        log.info("Пользователь с id {} удалён", id);
        userRepository.deleteById(id);
    }

    @Override
    public void isExistUser(long userId) {
        if (!userRepository.existsById(userId)) throw new ObjectNotFoundException("User not found: " + userId);
    }

    private User convertDtoToUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    private UserDto convertUserToDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}