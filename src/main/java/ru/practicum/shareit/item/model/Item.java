package ru.practicum.shareit.item.model;

import lombok.*;

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
}
