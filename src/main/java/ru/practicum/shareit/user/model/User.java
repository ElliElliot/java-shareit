package ru.practicum.shareit.user.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // — уникальный идентификатор пользователя;
    @Column(name = "name")
    private String  name; // — имя или логин пользователя;
    @Column(name = "email", nullable = false, unique = true)
    private String email; // — адрес электронной почты (два пользователя не могут иметь одинаковый адрес электронной почты).
}