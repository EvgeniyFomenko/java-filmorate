package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws FilmValidateException {
        try {
            validateFilm(film);
            return filmStorage.saveNewFilm(film);
        } catch (FilmValidateException ex) {
            log.warn(ex.getMessage());
            throw new FilmValidateException(ex.getMessage());
        }
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws FilmNotFoundException {
        if (filmStorage.isExist(film.getId())) {
            filmStorage.updateFilm(film);
        } else {
            log.warn("фильм с таким id не найден {}", film.getId());
            throw new FilmNotFoundException("фильм с таким id не найден " + film.getId());
        }
        return film;
    }

    @GetMapping
    public List<Film> getFilmList() {
        return new ArrayList<>(filmStorage.getFilmList().values());
    }

    private void validateFilm(Film film) throws FilmValidateException {
        if (Objects.isNull(film.getName()) || film.getName().isBlank() || film.getName().isEmpty()) {
            throw new FilmValidateException("Не заполнено название фильма");
        }

        if (film.getDescription().length() > 200) {
            throw new FilmValidateException("Длинна описания привышает 200 символов");
        }
        String dateStartFilmEpoch = "1895-12-28";
        if (film.getReleaseDate().isBefore(LocalDate.parse(dateStartFilmEpoch))) {
            throw new FilmValidateException("Дата выпуска не может быть раньше появления самого кино");
        }

        if (film.getDuration() <= 0) {
            throw new FilmValidateException("Продолжительность не может быть меньше или ровна 0");
        }
    }

}
