package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item getItem(long itemId);

    ItemDto getItemDto(long itemId, long userId);

    List<ItemDto> getAll(long id);

    List<ItemDto> searchItem(String text);

    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long userId, long itemId, ItemDto itemDto);

    void deleteItem(long id);

    CommentDto createComment(CommentDto commentDto, long itemId, long userId);
}