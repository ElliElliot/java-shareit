package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemController {
    private final ItemService itemService;

    @GetMapping ("/{itemId}")//Просмотр информации о конкретной вещи по её идентификатору
    public ItemDto getItem (@PathVariable int itemId){
        return itemService.getItem(itemId);
    }

    @GetMapping//Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой.
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") int id){
        return itemService.getAll(id);
    }

    @GetMapping("/search")//Поиск вещи потенциальным арендатором
    public List<ItemDto> searchItem(@RequestParam String text){
        return itemService.searchItem(text);
    }

    @PostMapping //добавление новой вещи
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") int userId, @Valid @RequestBody ItemDto itemDto){
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")//Редактирование вещи
    public ItemDto update(@RequestHeader ("X-Sharer-User-Id") int userId, @PathVariable int itemId, @RequestBody Item item){
        return itemService.update(userId, itemId, item);
    }
}
