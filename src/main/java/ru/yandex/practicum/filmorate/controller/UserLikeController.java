package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendsUser;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ManageFriendsUserService;
import ru.yandex.practicum.filmorate.service.Service;

import java.util.List;

@RestController
public class UserLikeController extends UserController {

    private final static String SERVICE = "ManageFriendsUserService";
    private final ManageFriendsUserService manageFriendsUserService;

    public UserLikeController(@Qualifier(SERVICE) Service filmService) {
        super(filmService);
        manageFriendsUserService = (ManageFriendsUserService) filmService;
    }

    @PutMapping("/{idUser}/friends/{idFriend}")
    public void addToFriends(@PathVariable int idUser, @PathVariable int idFriend) throws NotFoundException {
        manageFriendsUserService.addToFriends(new FriendsUser(idUser, idFriend));
    }

    @DeleteMapping("/{idUser}/friends/{idFriend}")
    public void deleteFromFriends(@PathVariable int idUser, @PathVariable int idFriend) {
        manageFriendsUserService.removeFriends(new FriendsUser(idUser, idFriend));
    }

    @GetMapping("/{idUser}/friends")
    public List<User> getFriend(@PathVariable Integer idUser) {
        return manageFriendsUserService.getFriends(idUser);
    }

    @GetMapping("/{idUser}/friends/common/{idFriend}")
    public List<User> getCommonFriend(@PathVariable int idUser, @PathVariable int idFriend) {
        return manageFriendsUserService.getCommonFriends(new FriendsUser(idUser, idFriend));
    }

}
