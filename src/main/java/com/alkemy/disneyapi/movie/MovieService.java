package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.mapstruct.dtos.MovieSlimDto;
import com.alkemy.disneyapi.mapstruct.dtos.MoviePostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    public Optional<Movie> findById(Long movieId) {

        return movieRepository.findById(movieId);

    }

    public Set<Movie> findByTitle(String title) {

        return movieRepository.findByTitle(title);

    }

    public void deleteById(Long id){

        movieRepository.deleteById(id);

    }

    public Movie save(Movie movie) {

        return movieRepository.save(movie);

    }

    public Set<Movie> getByGenreId(Long idGenre) {

        return movieRepository.findAll().stream().filter(x -> haveGenre(x, idGenre)).collect(Collectors.toSet());

    }

    private boolean haveGenre(Movie movie, Long idGenre) {

        return movie.getGenres().stream().anyMatch(g -> g.getId().equals(idGenre));

    }

}
