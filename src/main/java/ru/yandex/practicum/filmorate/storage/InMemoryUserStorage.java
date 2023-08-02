package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;
import java.util.Set;

@Component("inMemoryUserStorage")
@RequiredArgsConstructor
public class InMemoryUserStorage implements Storage {
    private final Map<Integer, User> userMap;
    private Integer id = 0;

    public boolean isExist(int id) {
        return userMap.containsKey(id);
    }

    public void update(Model model) {
        userMap.put(model.getId(), (User) model);
    }

    public Model save(Model model) {
        id++;
        model.setId(id);
        userMap.put(id, (User) model);
        return model;
    }

    public User get(int id) {
        return userMap.get(id);
    }

    public void delete(int id) {
        userMap.remove(id);
    }

    @Override
    public Map<Integer, ?> getModelMap() {
        return userMap;
    }

    public void removeIdFromIdSet(FromTo user) {
        Model model1 = userMap.get(user.getFrom());
        Model model2 = userMap.get(user.getTo());
        Set<Integer> userFr1 = model1.getIdSet();
        userFr1.remove(model2.getId());
        model1.setIdSet(userFr1);
        Set<Integer> userFr2 = model2.getIdSet();
        userFr2.remove(model1.getId());
        model1.setIdSet(userFr1);
    }

    public <T extends Model> T addToSet(FromTo user) {
        User user1 = userMap.get(user.getFrom());
        User user2 = userMap.get(user.getTo());
        Set<Integer> userFr1 = user1.getIdSet();
        userFr1.add(user2.getId());
        user1.setIdSet(userFr1);
        Set<Integer> userFr2 = user2.getIdSet();
        userFr2.add(user1.getId());
        user1.setIdSet(userFr1);
        return (T) user1;
    }

}
