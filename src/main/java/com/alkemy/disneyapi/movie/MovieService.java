package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.genre.Genre;
import com.alkemy.disneyapi.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {

        this.movieRepository = movieRepository;

        this.genreRepository = genreRepository;
    }

    public List<Movie> getAll() {

        return movieRepository.findAll();

    }

    public List<Movie> findAllOrderByCreationDate(String order) {

        if(order.equalsIgnoreCase("ASC")) {

            return movieRepository.findAllByOrderByCreationDateAsc();

        } else if (order.equalsIgnoreCase("DESC")) {

            return movieRepository.findAllByOrderByCreationDateDesc();

        }

        return null;

    }

    public Optional<Movie> findById(Long movieId) {

        return movieRepository.findById(movieId);

    }

    public List<Movie> findByTitle(String title) {

        return movieRepository.findByTitle(title);

    }

    public void deleteById(Long id){

        movieRepository.deleteById(id);

    }

    public Movie save(Movie movie) {


        return movieRepository.save(movie);

    }

    public List<Movie> getByGenreId(Long idGenre) {

        return movieRepository.findByGenre(idGenre);

    }

    public boolean checkGenresExistence(List<Long> genresIds) {

        return genreRepository.findAll().stream().map(Genre::getId).collect(Collectors.toList()).containsAll(genresIds);

    }

    public void addGenres(Long movieId, List<Long> genresIds) {

        Movie movie = movieRepository.getById(movieId);

        genreRepository.findAllById(genresIds).forEach(genre -> movie.getGenres().add(genre));

        movieRepository.save(movie);

    }

    public void removeGenres(Long movieId, List<Long> genresIds) {

        Movie movie = movieRepository.getById(movieId);

        movie.getGenres().removeIf(genre -> genresIds.contains(genre.getId()));

        movieRepository.save(movie);

    }

}
