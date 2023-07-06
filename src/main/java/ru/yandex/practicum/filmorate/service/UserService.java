package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @PostMapping
    public User addUser(@RequestBody User user) throws UserValidateException {
        try {
            validateUser(user);
            return userStorage.saveUser(user);
        } catch (UserValidateException ex) {
            log.warn(ex.getMessage());
            throw new UserValidateException(ex.getMessage());
        }
    }

    @PutMapping
    public void updateUser(@RequestBody User user) throws UserNotFoundException {
        if (userStorage.isExist(user.getId())) {
            userStorage.updateUser(user);
        } else {
            log.warn("Пользователя с таким id не существует");
            throw new UserNotFoundException("Пользователя с таким id не существует");
        }
    }

    @GetMapping
    public List<User> getUserList() {
        return new ArrayList<>(userStorage.getUserList().values());
    }

    private void validateUser(User user) throws UserValidateException {
        if (user.getEmail().isBlank() | !user.getEmail().contains("@")) {
            throw new UserValidateException("нет имейла или имейл не содержит знак @");
        }

        if (Objects.isNull(user.getLogin()) || user.getLogin().isBlank() || user.getLogin().isEmpty() ||
                user.getLogin().contains(" ")) {
            throw new UserValidateException("Логин пользователя не может содержать пробелы или быть пустым");
        }

        if (Objects.isNull(user.getName()) || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new UserValidateException("Дата рождения не может быть в будущем");
        }

    }
}
