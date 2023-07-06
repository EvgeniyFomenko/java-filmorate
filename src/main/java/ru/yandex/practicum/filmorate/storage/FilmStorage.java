package ru.yandex.practicum.filmorate.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;
@Data
@RequiredArgsConstructor
@Repository
public class FilmStorage {
    private final Map<Integer, Film> filmList;
    private Integer id = 0;


    public Film saveNewFilm(Film film) {
        id++;
        film.setId(id);
        filmList.put(id,film);
        return film;
    }

    public boolean isExist(int id) {
        return filmList.containsKey(id);
    }

    public void updateFilm(Film film) {
        filmList.put(film.getId(),film);
    }
}
