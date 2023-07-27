package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.Service;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/films")
public class FilmController extends Controller {
    private final static String USE_SERVICE = "FilmService";

    public FilmController(Service filmService) {
        super(filmService);
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film model) throws ValidateException {
        return super.add(model);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film model) throws NotFoundException {
        return super.update(model);
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable int id) throws NotFoundException {
        return super.get(id);
    }


}
