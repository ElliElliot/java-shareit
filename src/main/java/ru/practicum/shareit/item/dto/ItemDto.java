package ru.practicum.shareit.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class ItemDto {
    private int id; // — уникальный идентификатор вещи;
    @NotBlank
    private String name; // — краткое название;
    @NotBlank
    private String description; // — развёрнутое описание;
    @NotNull
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
}