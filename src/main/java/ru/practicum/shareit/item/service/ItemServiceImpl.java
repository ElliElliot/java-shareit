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
    public Object getItem(int itemId) {
        return itemRepository.getItem(itemId).orElseThrow(() -> {
            log.warn("Item not found");
            throw new ObjectNotFoundException("Item not found");
        });
        // Информацию о вещи может просмотреть любой пользователь
    }

    @Override
    public List<ItemDto> getAll(int id) {
        return itemRepository.getAll(id);
    }

    @Override
    public List<ItemDto> searchItem(String text) { //Поиск вещи потенциальным арендатором
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchItem(text);
        // Пользователь передаёт в строке запроса текст, и система ищет вещи, содержащие этот текст в названии или описании
        // в text передаётся текст для поиска. Проверьте, что поиск возвращает только доступные для аренды вещи.
    }

    @Override
    public ItemDto create(int userId, ItemDto itemDto) { //добавление новой вещи
        userRepository.getById(userId).orElseThrow(() -> {
            log.warn("Пользователь не найден");
            throw new ObjectNotFoundException("Пользователь не найден");
        });
        log.info("Предмет создан");
        return itemRepository.create(userId, itemDto);
        // User-Id — это идентификатор пользователя, который добавляет вещь.
        // Именно этот пользователь — владелец вещи
    }

    @Override
    public ItemDto update(int itemId, int userId, ItemDto itemDto) { //Редактирование вещи
        itemRepository.getItemForUpdate(userId, itemId).orElseThrow(() -> {
            log.warn("Обновление предмета невозможно");
            throw new ObjectNotFoundException("Обновление предмета невозможно");
        });
        log.info("Предмет обновлен");
        return itemRepository.update(itemId, userId, itemDto);
        // Изменить можно название, описание и статус доступа к аренде.
        // Редактировать вещь может только её владелец.
    }

    private void validate (int id) {

    }
}
