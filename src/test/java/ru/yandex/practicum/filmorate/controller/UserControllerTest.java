package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ManageFriendsUserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(new ManageFriendsUserService(new InMemoryUserStorage(new HashMap<>())));
    }

    @Test
    void add() throws ValidateException, NotFoundException {
        User user = new User();
        user.setEmail("my@email.ru");
        user.setLogin("login1");
        user.setBirthday(LocalDate.of(1994, 6, 12));

        User user1 = userController.add(user);
        assertEquals(1, userController.getModelList().size());
        user1.setName("Vasya");
        userController.update(user1);
        assertEquals("Vasya", ((User) userController.getModelList().get(0)).getName());
    }
}