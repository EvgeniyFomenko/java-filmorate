package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public abstract class Model {
    protected int id;
    protected String name;
    protected Set<Integer> idSet = new HashSet<>();
}
