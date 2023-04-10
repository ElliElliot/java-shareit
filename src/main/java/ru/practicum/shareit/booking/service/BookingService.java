package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReceivedBookingDto;

import java.util.List;


public interface BookingService {
    BookingDto getBooking(long bookingId, long userId);

    List<BookingDto> getAllUserBookings(long userId, String state, String user);

    BookingDto createBooking(ReceivedBookingDto bookingDto, long userId);

    BookingDto updateBookingStatus(long bookingId, String approved, long userId);
}