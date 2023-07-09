package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends Controller {

    public FilmController(FilmService service) {
        super(service);
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film model) throws ValidateException {
        return (Film) super.add(model);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film model) throws NotFoundException {
        return (Film) super.update(model);
    }
}
