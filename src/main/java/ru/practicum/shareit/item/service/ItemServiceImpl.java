package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;

    @Override
    public Object getItem(int itemId) {
        return itemRepository.getItem(itemId);
        // Информацию о вещи может просмотреть любой пользователь
    }

    @Override
    public List<ItemDto> getAll(int id) {
        return itemRepository.getAll(id);
    }

    @Override
    public List<ItemDto> searchItem(String text) { //Поиск вещи потенциальным арендатором
        return itemRepository.searchItem(text);
        // Пользователь передаёт в строке запроса текст, и система ищет вещи, содержащие этот текст в названии или описании
        // в text передаётся текст для поиска. Проверьте, что поиск возвращает только доступные для аренды вещи.
    }

    @Override
    public ItemDto create(int userId, ItemDto itemDto) { //добавление новой вещи
        return itemRepository.create(userId, itemDto);
        // User-Id — это идентификатор пользователя, который добавляет вещь.
        // Именно этот пользователь — владелец вещи
    }

    @Override
    public void update(int itemId) { //Редактирование вещи
        itemRepository.update(itemId);
        // Изменить можно название, описание и статус доступа к аренде.
        // Редактировать вещь может только её владелец.
    }

    private void validate (int id) {

    }
}
