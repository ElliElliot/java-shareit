package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Integer, List<Item>> items = new HashMap<>();//список вещей
    private int id = 0;
    @Override
    public Optional<ItemDto> getItem(int itemId) {//Просмотр информации о конкретной вещи по её идентификатору
        List<Item> allItems = new ArrayList<>();
        items.forEach((user, items1) -> allItems.addAll(items1));
        return allItems.stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst()
                .map(ItemMapper::toItemDto);
    }

    @Override
    public List<ItemDto> getAll(int ownerId) {//Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой
        List<Item> userItems = items.getOrDefault(ownerId, Collections.emptyList());
        return userItems.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        return null;
    }

    @Override
    public ItemDto create(int userId, ItemDto itemDto) { //добавление новой вещи
        itemDto.setId(id++);
        Item item = ItemMapper.toItem(itemDto, userId);
        items.compute(userId, (id, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        return ItemMapper.toItemDto(item);
    }

    @Override
    public void update(int itemId) {

    }
}
