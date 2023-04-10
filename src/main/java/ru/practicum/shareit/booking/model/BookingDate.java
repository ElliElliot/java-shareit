package ru.practicum.shareit.booking.model;

import java.time.LocalDateTime;

public interface BookingDate {
    long getId();

    LocalDateTime getBookingDate();

    long getBookerId();

}