package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage extends InMemoryStorage implements Storage {
    public InMemoryFilmStorage(Map<Integer, Model> modelMap) {
        super(modelMap);
    }
}
