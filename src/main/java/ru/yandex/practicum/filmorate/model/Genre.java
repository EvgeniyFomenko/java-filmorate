package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genre extends Model implements Comparable<Genre> {
    public Genre(int genreId, String name) {
        this.id = genreId;
        this.name = name;
    }

    @Override
    public int compareTo(Genre o) {
        return this.getId() - o.id;
    }
}
