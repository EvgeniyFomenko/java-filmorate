package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidateException;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.service.Service;

import java.util.List;

@RequiredArgsConstructor
public abstract class Controller<E extends Service> {

    private final Service service;


    public <T extends Model> T add(T model) throws ValidateException {
        service.addModel(model);
        return model;
    }


    public <T extends Model> T update(T model) throws NotFoundException {
        service.updateModel(model);
        return model;
    }

    @GetMapping
    public <T extends Model> List<? super Model> getModelList() {
        return service.getModelList();
    }
}
