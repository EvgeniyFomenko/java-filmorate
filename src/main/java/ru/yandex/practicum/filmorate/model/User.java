package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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
    private List<Integer> friends;

    public User() {
    }

    public User(int id, String name, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.friends = new LinkedList<>();
        this.birthday = birthday;
    }

    public User(int id, String name, List<Integer> friends, String email, String login, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.friends = friends;
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}
