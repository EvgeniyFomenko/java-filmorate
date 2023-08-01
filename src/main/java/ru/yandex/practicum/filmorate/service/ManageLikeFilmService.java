package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmLikes;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("ManageLikeFilmService")
public class ManageLikeFilmService extends FilmService {

    public ManageLikeFilmService(@Qualifier(FilmService.FILM_STORAGE) Storage storage) {
        super(storage);
    }

    public Film addLike(FilmLikes filmLikes) {
        return storage.addToSet(filmLikes);
    }

    public Film deleteLike(FilmLikes filmLikes) throws NotFoundException {
        Film film = super.getModelById(filmLikes.getFrom());
        Set<Integer> likes = film.getIdSet();
        int userId = filmLikes.getTo();
        if (userId < 1) {
            throw new NotFoundException(ManageFriendsUserService.NEGATIVE_ID_EXCEPTION);
        }
        likes.remove(userId);

        return film;
    }

    public Set<Integer> getLikes(Integer idFilm) {
        Film film = super.getModelById(idFilm);
        Set<Integer> likes = film.getIdSet();

        return likes;
    }

    public List<Film> getFilmsPopular(int count) {
        Collection<? super Model> collection = super.getModelList();
        List<Film> filmList = new ArrayList<>();
        for (Object fm :
                collection) {
            filmList.add((Film) fm);
        }

        return filmList.stream().sorted((e1, e2) -> e2.getIdSet().size() - e1.getIdSet().size())
                .limit(count).collect(Collectors.toList());
    }
}
