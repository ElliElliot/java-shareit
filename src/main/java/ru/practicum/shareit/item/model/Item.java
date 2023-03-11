package ru.practicum.shareit.item.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int id; // — уникальный идентификатор вещи;
    private String name; // — краткое название;
    private String description; // — развёрнутое описание;
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
    private int owner; //владелец вещи
    //private ItemRequest request; // — если вещь была создана по запросу другого пользователя, то в этом
//    поле будет храниться ссылка на соответствующий запрос.
}
