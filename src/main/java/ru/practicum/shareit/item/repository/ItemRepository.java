package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository {
    Optional<ItemDto> getItem (int itemId);
    List<ItemDto> getAll(int id);
    List<ItemDto>  searchItem(String text);
    ItemDto create(int userId, ItemDto itemDto);
    void update (int itemId);
}
