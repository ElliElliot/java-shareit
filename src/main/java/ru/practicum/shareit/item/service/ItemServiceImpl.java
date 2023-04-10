package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.BookingDate;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.InappropriateUser;
import ru.practicum.shareit.exception.ObjectNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ModelMapper mapper;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public Item getItem(long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> {
            log.warn("Предмет не найден");
            throw new ObjectNotFoundException("Предмет не найден");
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItemDto(long itemId, long userId) {
        Item item = getItem(itemId);
        item.getComments();
        ItemDto dto = convertItemToDto(item);
        if (item.getOwner() == userId) {
            dto.setLastBooking(bookingRepository.findLastBooking(itemId, LocalDateTime.now()));
            dto.setNextBooking(bookingRepository.findNextBooking(itemId, LocalDateTime.now()));
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> getAll(long ownerId) {
        List<ItemDto> items = itemRepository.findAllByOwner(ownerId).stream()
                .peek(Item::getComments)
                .map(this::convertItemToDto)
                .sorted(Comparator.comparing(ItemDto::getId))
                .collect(Collectors.toList());
        setBookingDate(items);
        return items;
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        if (text.isBlank()) return Collections.emptyList();
        return itemRepository.searchItem(text).stream()
                .map(this::convertItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ItemDto create(long userId, ItemDto itemDto) {
        userService.isExistUser(userId);
        Item item = mapper.map(itemDto, Item.class);
        item.setOwner(userId);
        log.info("Предмет создан");
        return convertItemToDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        isExistItem(itemId);
        Item item = itemRepository.findById(itemId).get();
        if (item.getOwner() != userId)
            throw new InappropriateUser("Пользователь с id: " + userId +
                    " не является владельцем предмета с id: " + itemId);
        if (itemDto.getName() != null) item.setName(itemDto.getName());
        if (itemDto.getDescription() != null) item.setDescription(itemDto.getDescription());
        if (itemDto.getAvailable() != null) item.setAvailable(itemDto.getAvailable());
        log.info("Предмет обновлен");
        return convertItemToDto(itemRepository.save(item));
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentDto commentDto, long itemId, long userId) {
        isValidComment(commentDto, itemId, userId);
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setAuthorName(userService.getUserById(userId).getName());
        comment.setItem(getItem(itemId));
        comment.setCreated(LocalDateTime.now());
        return convertCommentToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteItem(long userId) {
        itemRepository.deleteById(userId);
    }

    private void isValidComment(CommentDto commentDto, long itemId, long userId) {
        if (commentDto.getText().isBlank()) throw new BadRequestException("Комментарий не может быть пустым");
        if (!bookingRepository.existsBookingByBooker_IdAndItem_IdAndStatusAndStartBefore(userId, itemId,
                BookingStatus.APPROVED, LocalDateTime.now()))
            throw new BadRequestException("Пользователь " + userId +
                    " не может добавить комментарий к предмету с id^ " + itemId);
    }

    private ItemDto convertItemToDto(Item item) {
        return mapper.map(item, ItemDto.class);
    }

    private CommentDto convertCommentToDto(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }

    public void isExistItem(long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ObjectNotFoundException("Не найден предмет с id:" + itemId);
        }
    }

    private void setBookingDate(List<ItemDto> items) {
        List<Long> itemsId = items.stream()
                .map(ItemDto::getId).collect(Collectors.toList());
        List<BookingDate> allNextBooking = bookingRepository.findAllNextBooking(itemsId, LocalDateTime.now());
        if (!allNextBooking.isEmpty()) {
            for (int i = 0; i < allNextBooking.size(); i++) {
                items.get(i).setNextBooking(allNextBooking.get(i));
            }
        }
        List<BookingDate> allLastBooking = bookingRepository.findAllLastBooking(itemsId, LocalDateTime.now());
        if (!allLastBooking.isEmpty()) {
            for (int i = 0; i < allLastBooking.size(); i++) {
                items.get(i).setLastBooking(allLastBooking.get(i));
            }
        }
    }
}