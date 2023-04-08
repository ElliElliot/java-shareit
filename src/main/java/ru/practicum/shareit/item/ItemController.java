package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemController {
    private final ItemService itemService;
    private static final String USER_ID = "X-Sharer-User-Id";

    @GetMapping ("/{itemId}")//Просмотр информации о конкретной вещи по её идентификатору
    public ItemDto getItem(@PathVariable long itemId, @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItemDto(itemId,userId);
    }

    @GetMapping//Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой.
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") long id) {
        return itemService.getAll(id);
    }

    @GetMapping("/search")//Поиск вещи потенциальным арендатором
    public List<ItemDto> searchItem(@RequestParam String text) {
        return itemService.searchItem(text);
    }

    @PostMapping //добавление новой вещи
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId, @Valid @RequestBody ItemDto itemDto) {
        return itemService.create(userId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestBody CommentDto commentDto,
                                    @PathVariable long itemId,
                                    @RequestHeader(value = USER_ID) long userId) {
        return itemService.createComment(commentDto, itemId, userId);
    }

    @PatchMapping("/{itemId}")//Редактирование вещи
    public ItemDto update(@RequestHeader ("X-Sharer-User-Id") long userId,
                          @PathVariable long itemId, @RequestBody ItemDto itemDto) {;
        return itemService.update(userId, itemId, itemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable long id) {
        itemService.deleteItem(id);
    }
}