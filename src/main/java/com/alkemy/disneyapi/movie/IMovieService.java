package com.alkemy.disneyapi.movie;

import java.util.List;
import java.util.Optional;

public interface IMovieService {

    List<Movie> getAllMovies();

    List<Movie> findAllOrderByCreationDate(String order);

    Optional<Movie> findById(Long movieId);

    List<Movie> findByTitle(String title);

    void deleteById(Long id);

    Movie save(Movie movie);

    List<Movie> getByGenreId(Long idGenre);

    boolean checkGenresExistence(List<Long> genresIds);

    void addGenres(Long movieId, List<Long> genresIds);

    void removeGenres(Long movieId, List<Long> genresIds);

}
