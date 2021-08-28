package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.genre.Genre;
import com.alkemy.disneyapi.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<Movie> getAllMovies() {

        return movieRepository.findAll();

    }

    @Override
    public List<Movie> findAllOrderByCreationDate(String order) {

        if(order.equalsIgnoreCase("ASC")) {

            return movieRepository.findAllByOrderByCreationDateAsc();

        } else if (order.equalsIgnoreCase("DESC")) {

            return movieRepository.findAllByOrderByCreationDateDesc();

        }

        return null;

    }

    @Override
    public Optional<Movie> findById(Long movieId) {

        return movieRepository.findById(movieId);

    }

    @Override
    public List<Movie> findByTitle(String title) {

        return movieRepository.findByTitle(title);

    }

    @Override
    public void deleteById(Long id){

        movieRepository.deleteById(id);

    }

    @Override
    public Movie save(Movie movie) {


        return movieRepository.save(movie);

    }

    @Override
    public List<Movie> getByGenreId(Long idGenre) {

        return movieRepository.findByGenre(idGenre);

    }

    @Override
    public boolean checkGenresExistence(List<Long> genresIds) {

        return genreRepository.findAll().stream().map(Genre::getId).collect(Collectors.toList()).containsAll(genresIds);

    }

    @Override
    public void addGenres(Long movieId, List<Long> genresIds) {

        Movie movie = movieRepository.getById(movieId);

        genreRepository.findAllById(genresIds).forEach(genre -> movie.getGenres().add(genre));

        movieRepository.save(movie);

    }

    @Override
    public void removeGenres(Long movieId, List<Long> genresIds) {

        Movie movie = movieRepository.getById(movieId);

        movie.getGenres().removeIf(genre -> genresIds.contains(genre.getId()));

        movieRepository.save(movie);

    }

}
