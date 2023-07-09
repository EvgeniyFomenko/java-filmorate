package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class FilmService extends ru.yandex.practicum.filmorate.service.Service {
    public FilmService(FilmStorage storage) {
        super(storage);
    }

    protected void validate(Model model) throws ValidateException {
        Film film = (Film) model;
        if (Objects.isNull(film.getName()) || film.getName().isBlank() || film.getName().isEmpty()) {
            throw new ValidateException("Не заполнено название фильма");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidateException("Длинна описания привышает 200 символов");
        }
        String dateStartFilmEpoch = "1895-12-28";
        if (film.getReleaseDate().isBefore(LocalDate.parse(dateStartFilmEpoch))) {
            throw new ValidateException("Дата выпуска не может быть раньше появления самого кино");
        }

        if (film.getDuration() <= 0) {
            throw new ValidateException("Продолжительность не может быть меньше или ровна 0");
        }
    }

}
