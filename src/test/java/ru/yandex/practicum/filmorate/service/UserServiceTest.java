package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceTest {
    Service userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new UserStorage(new HashMap<>()));
    }

    @Test
    void addUser() throws ValidateException {
        User user = new User();
        user.setEmail("");
        user.setLogin("login1");
        user.setBirthday(LocalDate.of(1994, 6, 12));

        user.setEmail("my@email.ru");
        user.setLogin("vasya pupkin");
        assertThrows(ValidateException.class, () -> userService.addModel(user));
        user.setLogin("vasyaPupkin");
        userService.addModel(user);
        assertEquals(user.getLogin(), user.getName());
        user.setBirthday(LocalDate.of(2024, 2, 23));
        assertThrows(ValidateException.class, () -> userService.addModel(user));
    }
}