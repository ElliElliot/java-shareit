package ru.practicum.shareit.item.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // — уникальный идентификатор вещи;
    @Column(name = "name")
    private String name; // — краткое название;
    @Column(name = "description")
    private String description; // — развёрнутое описание;
    @Column(name = "is_available")
    private Boolean available; // — статус о том, доступна или нет вещь для аренды;
    @Column(name = "owner_id")
    private long owner; //владелец вещи
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private Set<Comment> comments;
}