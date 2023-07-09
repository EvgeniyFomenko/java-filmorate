package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;

@Data
public abstract class Storage {

    protected final Map<Integer, ? super Model> modelMap;
    private Integer id = 0;

    public boolean isExist(int id) {
        return modelMap.containsKey(id);
    }

    public <T extends Model> void update(T model) {
        modelMap.put(model.getId(), model);
    }

    public <T extends Model> T save(T model) {
        id++;
        model.setId(id);
        modelMap.put(id, model);
        return model;
    }
}
