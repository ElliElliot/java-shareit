package ru.practicum.shareit.exception;

public class AlreadyUsedEmail extends RuntimeException {
    public AlreadyUsedEmail(String message) {
        super(message);
    }
}