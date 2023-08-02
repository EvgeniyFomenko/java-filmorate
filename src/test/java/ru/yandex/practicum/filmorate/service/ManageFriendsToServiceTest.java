package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.FriendsTo;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class ManageFriendsToServiceTest {
    private final Storage userStorage = new InMemoryUserStorage(new HashMap<>());
    private final UserService userService = new UserService(userStorage);
    private final ManageFriendsUserService manageFriendsUserService = new ManageFriendsUserService(userStorage);

    @Test
    public void addFriend() throws ValidateException, NotFoundException {
        User user = new User();
        user.setBirthday(LocalDate.of(1994, 6, 12));
        user.setEmail("my@email.ru");
        user.setLogin("vasyaPupkin");
        user.setIdSet(new HashSet<>());


        User user1 = new User();
        user1.setBirthday(LocalDate.of(1994, 7, 12));
        user1.setEmail("123my@email.ru");
        user1.setLogin("vasislisaPupkina");
        user1.setIdSet(new HashSet<>());

        User user3 = new User();
        user3.setBirthday(LocalDate.of(1994, 7, 12));
        user3.setEmail("123@email.ru");
        user3.setLogin("vasisPupkin");
        user3.setIdSet(new HashSet<>());

        manageFriendsUserService.addToFriends(new FriendsTo(userService.addModel(user3).getId(), userService.addModel(user).getId()));
        manageFriendsUserService.addToFriends(new FriendsTo(userService.addModel(user1).getId(), user3.getId()));


        manageFriendsUserService.addToFriends(new FriendsTo(userService.addModel(user1).getId(), userService.addModel(user).getId()));
        System.out.println(user1.getIdSet());
        System.out.println(manageFriendsUserService.getCommonFriends(new FriendsTo(user.getId(), user1.getId())));
    }
}
