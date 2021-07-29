package com.alkemy.disneyapi.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {

        this.movieRepository = movieRepository;

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

        return movieRepository.findAll().stream().filter(x -> haveGenre(x, idGenre)).collect(Collectors.toList());

    }

    private boolean haveGenre(Movie movie, Long idGenre) {

        return movie.getGenres().stream().anyMatch(g -> g.getId().equals(idGenre));

    }

}
