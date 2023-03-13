package ru.practicum.shareit.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private int id;
    @NotBlank
    private String  name;
    @NotNull
    @Email
    private String email;
}
