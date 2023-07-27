package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.Objects;

@Service("UserService")
public class UserService extends ru.yandex.practicum.filmorate.service.Service {
    protected final static String USER_STORAGE = "inMemoryUserStorage";
    protected final static String USER_LOGIN_EXCEPTION = "Логин пользователя не может содержать пробелы";
    protected final static String USER_BIRTH_DATE_EXCEPTION = "Дата рождения не может быть в будущем";

    public UserService(@Qualifier(USER_STORAGE) Storage storage) {
        super(storage);
    }

    @Override
    protected void validate(Model model) throws ValidateException {
        User user = (User) model;

        if (user.getLogin().contains(" ")) {
            throw new ValidateException(USER_LOGIN_EXCEPTION);
        }

        if (Objects.isNull(user.getName()) || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidateException(USER_BIRTH_DATE_EXCEPTION);
        }

    }
}
