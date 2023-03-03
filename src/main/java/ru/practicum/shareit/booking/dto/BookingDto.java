package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
public class BookingDto {
    int id; // — уникальный идентификатор бронирования;
    LocalDateTime start; // — дата и время начала бронирования;
    LocalDateTime end; // — дата и время конца бронирования;
    Item item; // — вещь, которую пользователь бронирует;
    User booker; // — пользователь, который осуществляет бронирование;
    BookingStatus status; // — статус бронирования.
}
