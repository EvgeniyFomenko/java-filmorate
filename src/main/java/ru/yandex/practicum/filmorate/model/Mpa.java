package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Mpa extends Model {

    public Mpa(int mpaId) {
        this.id = mpaId;
        this.name = "";
    }

    public Mpa(int mpaId, String name) {
        this.id = mpaId;
        this.name = name;
    }
}
