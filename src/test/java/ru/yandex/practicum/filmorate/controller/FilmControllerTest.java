package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController(new FilmService(new FilmStorage(new HashMap<>())));
    }

    @Test
    void add() throws ValidateException, NotFoundException {
        Film film = new Film();
        film.setDescription("description");
        film.setDuration(60);
        film.setReleaseDate(LocalDate.of(2022, 02, 23));

        assertThrows(ValidateException.class, () -> filmController.add(film));
        film.setName("film1");
        Film film1 = filmController.add(film);
        assertEquals(1, filmController.getModelList().size());
        film1.setName("FilmVasya");
        filmController.update(film1);
        assertEquals("FilmVasya", ((Film) filmController.getModelList().get(0)).getName());
    }
}