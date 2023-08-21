package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDBStorageTest {

    private final FilmDBStorage storageFilm;
    private final UserDBStorage userDBStorage;
    TreeSet<Genre> genreTreeSet = new TreeSet<>();
    TreeSet<Integer> likes = new TreeSet<>();
    User user = new User(0, "user", "user@mail.ru", "userLogin", LocalDate.of(1993, 2, 12));
    Film film = new Film(0, "name", genreTreeSet, "description", LocalDate.of(2021, 3, 23)
            , 45, new Mpa(1), likes);
    Film film2 = new Film(0, "name2", genreTreeSet, "description2", LocalDate.of(2021, 3, 23)
            , 45, new Mpa(1), likes);

    @Test
    public void save() {
        userDBStorage.save(user);

        Collections.addAll(genreTreeSet, new Genre(1, null), new Genre(3, null));

        Collections.addAll(likes, 1);

        storageFilm.save(film);
        storageFilm.save(film2);
        Film filmGet = storageFilm.get(1);

        assertEquals(1, filmGet.getId());
        assertEquals(2, storageFilm.get(2).getId());

    }

    @Test
    public void update() {
        storageFilm.save(film);

        Film updateWithName = new Film(1, "nameUpdate", genreTreeSet, "descriptionUpdate",
                LocalDate.of(2021, 3, 23), 45, new Mpa(1), likes);

        storageFilm.update(updateWithName);

        Film updateGet = storageFilm.get(1);

        assertEquals(updateGet.getName(), "nameUpdate");

        Collections.addAll(genreTreeSet, new Genre(2, null));

        Film updateWithGenre = new Film(1, "nameUpdate", genreTreeSet, "descriptionUpdate",
                LocalDate.of(2021, 3, 23), 45, new Mpa(1), likes);
        storageFilm.update(updateWithGenre);
        assertTrue(storageFilm.get(1).getGenres().stream().anyMatch(e -> Objects.equals(e.getId(), 2)));
    }

}