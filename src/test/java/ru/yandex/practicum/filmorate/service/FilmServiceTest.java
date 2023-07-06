package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.FilmValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
    FilmService filmService;
    @BeforeEach
    void setUp() {
        filmService = new FilmService(new FilmStorage(new HashMap<>()));
    }

    @Test
    void addFilm() {
        Film film = new Film();
        film.setDescription("description");
        film.setDuration(60);
        film.setReleaseDate(LocalDate.of(2022,02,23));

        assertThrows(FilmValidateException.class,()->filmService.addFilm(film)) ;
        film.setName(" ");
        assertThrows(FilmValidateException.class,()->filmService.addFilm(film)) ;
        film.setDescription(" wqeqwrwefsdgsdevygfghdfgsdrthdfhdfgsgsdgdsgdgfdfgdfggggggfdffhljhjlhkjlhiuhiulhjlknjhkjdsafjdfasofuawejflkasjfls;jfiasojfeiajs;fe;kasjdfksjfdiosjfisjfsidhgdfgskdjfksdjfjsif fjs kfj sdkfjas;lfd ;sjfkas");
        film.setName("film1");
        assertThrows(FilmValidateException.class,()->filmService.addFilm(film)) ;
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895,12,25));
        assertThrows(FilmValidateException.class,()->filmService.addFilm(film)) ;
        film.setReleaseDate(LocalDate.of(2022,02,23));
        film.setDuration(-1);
        assertThrows(FilmValidateException.class,()->filmService.addFilm(film)) ;
    }
}