package ru.practicum.shareit.exception;

public class InappropriateUser extends RuntimeException {
    public InappropriateUser(String message) {
        super(message);
    }
}