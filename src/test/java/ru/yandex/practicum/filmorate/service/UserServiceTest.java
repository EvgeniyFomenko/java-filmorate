package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.UserValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    @BeforeEach
    void setUp(){
        userService = new UserService(new UserStorage(new HashMap<>()));
    }

    @Test
    void addUser() throws UserValidateException {
        User user = new User();
        user.setEmail("");
        user.setLogin("login1");
        user.setBirthday(LocalDate.of(1994, 6,12));

        assertThrows(UserValidateException.class,()->userService.addUser(user)) ;
        user.setEmail("myemail");
        assertThrows(UserValidateException.class,()->userService.addUser(user)) ;
        user.setEmail("my@email.ru");
        user.setLogin("");
        assertThrows(UserValidateException.class,()->userService.addUser(user)) ;
        user.setLogin("vasya pupkin");
        assertThrows(UserValidateException.class,()->userService.addUser(user)) ;
        user.setLogin("vasyaPupkin");
        userService.addUser(user);
        assertEquals(user.getLogin(),user.getName());
        user.setBirthday(LocalDate.of(2024,2,23));
        assertThrows(UserValidateException.class,()->userService.addUser(user));
    }
}