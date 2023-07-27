package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage extends InMemoryStorage implements Storage {

    public InMemoryUserStorage(Map<Integer, Model> modelMap) {
        super(modelMap);
    }

}
