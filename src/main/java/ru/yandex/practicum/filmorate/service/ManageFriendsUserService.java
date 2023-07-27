package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendsUser;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;
import java.util.stream.Collectors;


@Service("ManageFriendsUserService")
public class ManageFriendsUserService extends UserService {
    protected final static String NEGATIVE_ID_EXCEPTION = "Id пользователя не может быть меньше нуля";

    public ManageFriendsUserService(@Qualifier(UserService.USER_STORAGE) Storage storage) {
        super(storage);
    }

    public void addToFriends(FriendsUser user) throws NotFoundException {
        if (user.getIdFrom() < 1 || user.getIdTo() < 1) {
            throw new NotFoundException(NEGATIVE_ID_EXCEPTION);
        }
        User user1 = super.getModelById(user.getIdFrom());
        User user2 = super.getModelById(user.getIdTo());
        Set<Integer> userFr1 = user1.getFriendsList();
        userFr1.add(user2.getId());
        user1.setFriendsList(userFr1);
        Set<Integer> userFr2 = user2.getFriendsList();
        userFr2.add(user1.getId());
        user1.setFriendsList(userFr1);
    }

    public void removeFriends(FriendsUser user) {
        User user1 = super.getModelById(user.getIdFrom());
        User user2 = super.getModelById(user.getIdTo());
        Set<Integer> userFr1 = user1.getFriendsList();
        userFr1.remove(user2.getId());
        user1.setFriendsList(userFr1);
        Set<Integer> userFr2 = user2.getFriendsList();
        userFr2.remove(user1.getId());
        user1.setFriendsList(userFr1);
    }

    public List<User> getCommonFriends(FriendsUser user) {
        User user1 = super.getModelById(user.getIdFrom());
        User user2 = super.getModelById(user.getIdTo());
        List<Integer> idFriends = user1.getFriendsList().stream().filter(e -> user2.getFriendsList().stream()
                .anyMatch(e1 -> Objects.equals(e, e1))).collect(Collectors.toList());
        return idFriends.stream().map(super::getModelById).collect(Collectors.toList()).stream().map(e -> (User) e)
                .collect(Collectors.toList());
    }

    public List<User> getFriends(int id) {
        User user = super.getModelById(id);
        Collection<? super Model> filmList = super.getModelList();
        List<User> lObj = new ArrayList<>();

        for (Object fm :
                filmList) {
            lObj.add((User) fm);
        }

        List<User> usersList = lObj.stream().filter(e -> user.getFriendsList().contains(e.getId()))
                .collect(Collectors.toList());

        return usersList;
    }
}
