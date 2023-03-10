package ru.practicum.shareit.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int id; // — уникальный идентификатор пользователя;
    @NonNull
    @NotBlank
    private String  name; // — имя или логин пользователя;
    @Email
    @NonNull
    private String email; // — адрес электронной почты (два пользователя не могут иметь одинаковый адрес электронной почты).
}
