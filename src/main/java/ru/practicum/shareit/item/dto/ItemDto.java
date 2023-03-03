package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.item.model.ItemStatus;

/**
 * TODO Sprint add-controllers.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private int id; // — уникальный идентификатор вещи;
    //@NonNull
    private String name; // — краткое название;
    private String description; // — развёрнутое описание;
    //@NonNull
    private ItemStatus available; // — статус о том, доступна или нет вещь для аренды;

}
