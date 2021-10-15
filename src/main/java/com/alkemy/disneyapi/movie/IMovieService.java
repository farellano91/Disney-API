package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.genre.Genre;

import java.util.List;
import java.util.Set;

public interface IMovieService {

    List<Movie> getAll();

    List<Movie> findAllOrderByCreationDate(String order);

    Movie findById(Long movieId);

    List<Movie> findByTitle(String title);

    void delete(Long id);

    Movie save(Movie movie);

    List<Movie> findByGenreId(Long idGenre);

    Set<Genre> getGenres(Long id);

    void addGenres(Long movieId, List<Long> genresIds);

    void removeGenres(Long movieId, List<Long> genresIds);

}
