package ru.practicum.shareit.user.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id; // — уникальный идентификатор пользователя;
    private String  name; // — имя или логин пользователя;
    private String email; // — адрес электронной почты (два пользователя не могут иметь одинаковый адрес электронной почты).
}
