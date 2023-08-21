package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

@Slf4j
@RequiredArgsConstructor
public abstract class Service {
    public static String NOT_FOUND_ID_EXCEPTION = "модель с требуемым id = %d не найдена";

    protected final Storage storage;

    public <T extends Model> T addModel(T model) throws ValidateException {
        try {
            validate(model);
            if (model instanceof Film) {
                Film film = (Film) model;
                if (Objects.isNull(film.getGenres())) {
                    film.setGenres(new TreeSet<>());
                }

                if (Objects.isNull(film.getLikes())) {
                    film.setLikes(new TreeSet<>());
                }
            }
            return (T) storage.save(model);
        } catch (ValidateException ex) {
            log.warn(ex.getMessage());
            throw new ValidateException(ex.getMessage());
        }
    }

    public <T extends Model> T updateModel(T model) throws NotFoundException {
        try {
            if (storage.isExist(model.getId())) {
                if (model instanceof Film) {
                    Film film = (Film) model;
                    if (Objects.isNull(film.getGenres())) {
                        film.setGenres(new TreeSet<>());
                    }

                    if (Objects.isNull(film.getLikes())) {
                        film.setLikes(new TreeSet<>());
                    }
                }
                storage.update(model);

            }
        } catch (EmptyResultDataAccessException exception) {
            log.warn(String.format(NOT_FOUND_ID_EXCEPTION, model.getId()));
            throw new NotFoundException(String.format(NOT_FOUND_ID_EXCEPTION, model.getId()));
        }

        return model;
    }

    public <T extends Model> List<? super Model> getModelList() {
        return new ArrayList<>(storage.getModelMap().values());
    }

    protected abstract <T extends Model> void validate(T model) throws ValidateException;

    public <T extends Model> T getModelById(int id) throws NotFoundException {
        try {
            return storage.get(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(String.format(NOT_FOUND_ID_EXCEPTION, id));
        }
    }


}
