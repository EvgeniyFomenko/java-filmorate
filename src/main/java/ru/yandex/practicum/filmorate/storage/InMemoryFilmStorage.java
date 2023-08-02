package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.Map;
import java.util.Set;

@Component("inMemoryFilmStorage")
@RequiredArgsConstructor
public class InMemoryFilmStorage implements Storage {
    protected final Map<Integer, Film> filmMap;
    private Integer id = 0;

    public boolean isExist(int id) {
        return filmMap.containsKey(id);
    }

    public void update(Model model) {
        filmMap.put(model.getId(), (Film) model);
    }

    public Model save(Model model) {
        id++;
        model.setId(id);
        filmMap.put(id, (Film) model);
        return model;
    }

    public Film get(int id) {
        return filmMap.get(id);
    }

    public void delete(int id) {
        filmMap.remove(id);
    }

    public Map<Integer, ?> getModelMap() {
        return filmMap;
    }

    public void removeIdFromIdSet(FromTo films) {
        Model model1 = filmMap.get(films.getFrom());
        Model model2 = filmMap.get(films.getTo());
        Set<Integer> userFr1 = model1.getIdSet();
        userFr1.remove(model2.getId());
        model1.setIdSet(userFr1);
        Set<Integer> userFr2 = model2.getIdSet();
        userFr2.remove(model1.getId());
        model1.setIdSet(userFr1);
    }

    @Override
    public <T extends Model> T addToSet(FromTo filmLikes) {
        Film film = filmMap.get(filmLikes.getFrom());
        Set<Integer> likes = film.getIdSet();

        likes.add(filmLikes.getTo());
        return (T) film;
    }

}
