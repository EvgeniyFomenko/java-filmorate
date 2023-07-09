package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class UserService extends ru.yandex.practicum.filmorate.service.Service {

    public UserService(UserStorage storage) {
        super(storage);
    }

    @Override
    protected void validate(Model model) throws ValidateException {
        User user = (User) model;

        if (user.getLogin().contains(" ")) {
            throw new ValidateException("Логин пользователя не может содержать пробелы");
        }

        if (Objects.isNull(user.getName()) || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidateException("Дата рождения не может быть в будущем");
        }

    }
}
