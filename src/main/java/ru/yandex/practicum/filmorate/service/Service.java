package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class Service {

    private final Storage storage;

    public <T extends Model> T addModel(T model) throws ValidateException {
        try {
            validate(model);
            return storage.save(model);
        } catch (ValidateException ex) {
            log.warn(ex.getMessage());
            throw new ValidateException(ex.getMessage());
        }
    }

    public <T extends Model> T updateModel(T model) throws NotFoundException {
        if (storage.isExist(model.getId())) {
            storage.update(model);
        } else {
            log.warn("фильм с таким id не найден {}", model.getId());
            throw new NotFoundException("фильм с таким id не найден " + model.getId());
        }
        return model;
    }

    public <T extends Model> List<? super Model> getModelList() {
        return new ArrayList<>(storage.getModelMap().values());
    }

    protected abstract <T extends Model> void validate(T model) throws ValidateException;


}