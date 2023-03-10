package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

public class ItemRequestDto {
    int id; // — уникальный идентификатор запроса;
    String description; // — текст запроса, содержащий описание требуемой вещи;
    User requestor; // — пользователь, создавший запрос;
    LocalDateTime created; // — дата и время создания запроса.
}
