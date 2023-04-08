package ru.practicum.shareit.exception;

public class BookingStatusAlreadySet extends RuntimeException {
    public BookingStatusAlreadySet(String message) {
        super(message);
    }
}