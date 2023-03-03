package ru.practicum.shareit.user.model;

import javax.validation.constraints.Email;

/**
 * TODO Sprint add-controllers.
 */
public class User {
    int id; // — уникальный идентификатор пользователя;
    String  name; // — имя или логин пользователя;
    @Email
    String email; // — адрес электронной почты (два пользователя не могут иметь одинаковый адрес электронной почты).
}
