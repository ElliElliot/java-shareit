package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<ItemDto> getItem(int itemId);

    List<ItemDto> getAll(int id);

    List<ItemDto> searchItem(String text);

    ItemDto create(int userId, ItemDto itemDto);

    ItemDto update(int userId, int itemId, Item item);

    Optional<ItemDto> getItemForUpdate(int userId, int itemId);
    
}