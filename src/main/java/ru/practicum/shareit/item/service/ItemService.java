package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Object getItem (int itemId);
    List<ItemDto> getAll(int id);
    List<ItemDto>  searchItem(String text);
    ItemDto create(int userId, ItemDto itemDto);
    ItemDto update (int userId, int itemId, Item item);
}
