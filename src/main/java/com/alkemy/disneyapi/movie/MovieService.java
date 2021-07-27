package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.dtos.MovieGetLiteDto;
import com.alkemy.disneyapi.dtos.MoviePostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Set<MovieGetLiteDto> getAll() {

        return movieRepository.findAll().stream().map(MovieGetLiteDto::new).collect(Collectors.toSet());

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

    public Movie update(MoviePostDto movie, Movie movieToUpdate) {

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setImage(movie.getImage());
        movieToUpdate.setCreationDate(movie.getCreationDate());
        movieToUpdate.setRating(movie.getRating());

        return movieRepository.save(movieToUpdate);

    }

}
