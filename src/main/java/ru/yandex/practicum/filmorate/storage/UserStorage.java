package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;

@Repository
public class UserStorage extends Storage {

    public UserStorage(Map<Integer, Model> modelMap) {
        super(modelMap);
    }
}
