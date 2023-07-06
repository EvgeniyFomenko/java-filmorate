package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Data
@RequiredArgsConstructor
@Repository
public class UserStorage {
    private final Map<Integer, User> userList;
    private Integer id = 0;

    public User saveUser(User user) {
        id++;
        user.setId(id);
        userList.put(id, user);
        return user;
    }

    public boolean isExist(int id) {
        return userList.containsKey(id);
    }

    public void updateUser(User user) {
        userList.put(user.getId(), user);
    }
}
