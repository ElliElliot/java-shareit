package ru.practicum.shareit.exception;

public class ItemIsUnavailable extends RuntimeException {
    public ItemIsUnavailable(String message) {
        super(message);
    }
}