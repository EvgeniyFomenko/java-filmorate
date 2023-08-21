package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.storage.StorageUser;

@Slf4j
public abstract class ServiceUser extends Service {

    protected ServiceUser(StorageUser storage) {
        super(storage);
        NOT_FOUND_ID_EXCEPTION = "Пользователь с требуемым id не найден";
    }


}
