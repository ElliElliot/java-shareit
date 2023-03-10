package ru.practicum.shareit.booking.model;

public enum BookingStatus { // — статус бронирования.

    WAITING, // — новое бронирование, ожидает одобрения
    APPROVED, // - бронирование подтверждено владельцем
    REJECTED, // — бронирование отклонено владельцем
    CANCELED // — бронирование отменено создателем

}
