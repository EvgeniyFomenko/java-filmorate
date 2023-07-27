package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;

public interface Storage {

    public boolean isExist(int id);

    public <T extends Model> void update(T model);

    public <T extends Model> T save(T model);

    public <T extends Model> T get(int id);

    public void delete(int id);

    Map<Integer, ? super Model> getModelMap();
}
