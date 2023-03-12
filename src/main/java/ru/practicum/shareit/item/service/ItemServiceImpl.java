package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto getItem(int itemId) {
        return itemRepository.getItem(itemId).orElseThrow(() -> {
            log.warn("Предмет не найден");
            throw new ObjectNotFoundException("Предмет не найден");
        });
    }

    @Override
    public List<ItemDto> getAll(int id) {
        return itemRepository.getAll(id);
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchItem(text.toLowerCase());
    }

    @Override
    public ItemDto create(int userId, ItemDto itemDto) {
        userRepository.getById(userId).orElseThrow(() -> {
            log.warn("Пользователь не найден");
            throw new ObjectNotFoundException("Пользователь не найден");
        });
        log.info("Предмет создан");
        return itemRepository.create(userId, itemDto);
    }

    @Override
    public ItemDto update(int userId, int itemId, Item item) {
        itemRepository.getItemForUpdate(userId, itemId).orElseThrow(() -> {
            log.warn("Обновление предмета невозможно");
            throw new ObjectNotFoundException("Обновление предмета невозможно");
        });
        log.info("Предмет обновлен");
        return itemRepository.update(userId, itemId, item);
    }
}
