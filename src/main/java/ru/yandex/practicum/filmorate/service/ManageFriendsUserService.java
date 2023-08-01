package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendsTo;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("ManageFriendsUserService")
public class ManageFriendsUserService extends UserService {
    protected static final String NEGATIVE_ID_EXCEPTION = "Id пользователя не может быть меньше нуля";

    public ManageFriendsUserService(@Qualifier(UserService.USER_STORAGE) Storage storage) {
        super(storage);
    }

    public void addToFriends(FriendsTo users) throws NotFoundException {
        if (users.getFrom() < 1 || users.getTo() < 1) {
            throw new NotFoundException(NEGATIVE_ID_EXCEPTION);
        }
        storage.addToSet(users);
    }

    public void removeFriends(FriendsTo user) {
        storage.removeIdFromIdSet(user);
    }

    public List<User> getCommonFriends(FriendsTo user) {
        User user1 = super.getModelById(user.getFrom());
        User user2 = super.getModelById(user.getTo());
        List<Integer> idFriends = user1.getIdSet().stream().filter(e -> user2.getIdSet().stream()
                .anyMatch(e1 -> Objects.equals(e, e1))).collect(Collectors.toList());
        return idFriends.stream().map(super::getModelById).collect(Collectors.toList()).stream().map(e -> (User) e)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(int id) {
        User user = super.getModelById(id);
        List<User> userList = new ArrayList<>();

        for (int idUser : user.getIdSet()) {
            userList.add(super.getModelById(idUser));
        }

        return userList;
    }
}
