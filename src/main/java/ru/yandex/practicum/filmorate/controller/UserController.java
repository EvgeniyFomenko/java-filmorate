package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends Controller {

    public UserController(UserService service) {
        super(service);
    }

    @PostMapping
    public User add(@RequestBody @Valid User model) throws ValidateException {
        return (User) super.add(model);
    }

    @PutMapping
    public User update(@RequestBody @Valid User model) throws NotFoundException {
        return (User) super.update(model);
    }
}
