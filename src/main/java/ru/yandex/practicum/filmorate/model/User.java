package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User extends Model {
    @Email
    @NotBlank
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    private String login;
    private LocalDate birthday;
    private Set<Integer> friendsList = new HashSet<>();
}
