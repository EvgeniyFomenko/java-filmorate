package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.TreeSet;

@Data
public class Film extends Model {
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Mpa mpa;
    private TreeSet<Genre> genres = new TreeSet<>();
    private TreeSet<Integer> likes = new TreeSet<>();

    public Film() {
    }

    public Film(int id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(int id, String name, String description, LocalDate releaseDate, int duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

    public Film(int id, String name, TreeSet<Genre> genres, String description, LocalDate releaseDate, int duration,
                Mpa mpa, TreeSet<Integer> likes) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.likes = likes;
    }


}
