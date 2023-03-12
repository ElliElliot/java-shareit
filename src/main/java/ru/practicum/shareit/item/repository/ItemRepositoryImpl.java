package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Integer, List<Item>> items = new HashMap<>();
    private int id = 1;

    @Override
    public Optional<ItemDto> getItem(int itemId) {
        List<Item> allItems = new ArrayList<>();
        items.forEach((user, items1) -> allItems.addAll(items1));
        log.info("Предмет отправлен");
        return allItems.stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst()
                .map(ItemMapper::toItemDto);
    }

    @Override
    public List<ItemDto> getAll(int ownerId) {
        List<Item> userItems = items.getOrDefault(ownerId, Collections.emptyList());
        return userItems.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(int userId, ItemDto itemDto) {
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
    public ItemDto update(int userId, int itemId, Item item) {
        Item repoItem = items.get(userId).stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst()
                .get();
        if (item.getName() != null) repoItem.setName(item.getName());
        if (item.getDescription() != null) repoItem.setDescription(item.getDescription());
        if (item.getAvailable() != null) repoItem.setAvailable(item.getAvailable());
        items.get(userId).removeIf(item1 -> item1.getId() == itemId);
        items.get(userId).add(repoItem);
        return ItemMapper.toItemDto(repoItem);
    }

    @Override
    public Optional<ItemDto> getItemForUpdate(int userId, int itemId) {
        return items.getOrDefault(userId, Collections.emptyList()).stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst()
                .map(ItemMapper::toItemDto);
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        List<Item> allItems = new ArrayList<>();
        items.forEach((userId, items1) -> allItems.addAll(items.get(userId)));
        return allItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(text) ||
                        item.getDescription().toLowerCase().contains(text))
                .filter(Item::getAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
