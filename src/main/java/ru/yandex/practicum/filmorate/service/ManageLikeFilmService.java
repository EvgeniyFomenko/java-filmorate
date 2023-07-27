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
        Film film = super.getModelById(filmLikes.getIdFilm());
        Set<Integer> likes = film.getLikes();

        likes.add(filmLikes.getIdUser());

        return film;
    }

    public Film deleteLike(FilmLikes filmLikes) throws NotFoundException {
        Film film = super.getModelById(filmLikes.getIdFilm());
        Set<Integer> likes = film.getLikes();
        int userId = filmLikes.getIdUser();
        if (userId < 1) {
            throw new NotFoundException(ManageFriendsUserService.NEGATIVE_ID_EXCEPTION);
        }
        likes.remove(userId);

        return film;
    }

    public Set<Integer> getLikes(Integer idFilm) {
        Film film = super.getModelById(idFilm);
        Set<Integer> likes = film.getLikes();

        return likes;
    }

    public List<Film> getFilmsPopular(int count) {
        Collection<? super Model> filmList = super.getModelList();
        List<Film> lObj = new ArrayList<>();
        for (Object fm :
                filmList) {
            lObj.add((Film) fm);
        }

        return lObj.stream().sorted((e1, e2) -> e2.getLikes().size() - e1.getLikes().size())
                .limit(count).collect(Collectors.toList());
    }
}
