package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class FilmService extends ru.yandex.practicum.filmorate.service.Service {

    protected final static String FILM_STORAGE = "inMemoryFilmStorage";
    protected final static String FILM_NAME_BLANK_EXCEPTION = "Не заполнено название фильма";
    protected final static String FILM_DESCRIPTION_EXCEPTION = "Длинна описания привышает 200 символов";
    protected final static String FILM_DATE_PRODUCE_EXCEPTION = "Дата выпуска не может быть раньше появления самого кино";
    protected final static String FILM_DURATION_EXCEPTION = "Продолжительность не может быть меньше или ровна 0";


    public FilmService(@Qualifier(FILM_STORAGE) Storage storage) {
        super(storage);
    }

    protected void validate(Model model) throws ValidateException {
        Film film = (Film) model;
        if (Objects.isNull(film.getName()) || film.getName().isBlank() || film.getName().isEmpty()) {
            throw new ValidateException(FILM_NAME_BLANK_EXCEPTION);
        }

        if (film.getDescription().length() > 200) {
            throw new ValidateException(FILM_DESCRIPTION_EXCEPTION);
        }
        final String dateStartFilmEpoch = "1895-12-28";
        if (film.getReleaseDate().isBefore(LocalDate.parse(dateStartFilmEpoch))) {
            throw new ValidateException(FILM_DATE_PRODUCE_EXCEPTION);
        }

        if (film.getDuration() <= 0) {
            throw new ValidateException(FILM_DURATION_EXCEPTION);
        }
    }

}
