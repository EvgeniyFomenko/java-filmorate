package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film extends Model {
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
