package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.model.BookingDate;
import ru.practicum.shareit.item.model.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private long id; // — уникальный идентификатор вещи;
    @NotBlank
    private String name; // — краткое название;
    @NotBlank
    private String description; // — развёрнутое описание;
    @NotNull
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
    private BookingDate lastBooking;
    private BookingDate nextBooking;
    private Set<Comment> comments;
}