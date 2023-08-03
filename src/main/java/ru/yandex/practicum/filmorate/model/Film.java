package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
public class Film extends Model {
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private List<String> genre;
    private MPAStatus mpa;//Motion Picture Association

}
