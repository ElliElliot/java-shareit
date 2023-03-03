package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Service
public interface ItemService {
    Object getItem (int itemId);
    List<ItemDto> getAll(int id);
    List<ItemDto>  searchItem(String text);
    ItemDto create(int userId, ItemDto itemDto);
    void update (int itemId);
}
