package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    long id; // — уникальный идентификатор бронирования;
    LocalDateTime start; // — дата и время начала бронирования;
    LocalDateTime end; // — дата и время конца бронирования;
    Item item; // — вещь, которую пользователь бронирует;
    User booker; // — пользователь, который осуществляет бронирование;
    BookingStatus status; // — статус бронирования.
}