package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.dtos.MovieGetFullDto;
import com.alkemy.disneyapi.dtos.MovieGetLiteDto;
import com.alkemy.disneyapi.dtos.MoviePostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    public ResponseEntity<Set<MovieGetLiteDto>> getAll() {

        Set<MovieGetLiteDto> movies = movieService.getAll();

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(movies, HttpStatus.OK);

        }
    }

    //GET MOVIE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieGetFullDto> findById(@PathVariable("id") Long movieId) {

        Optional<Movie> movie = movieService.findById(movieId);

        return movie.map(value -> new ResponseEntity<>(new MovieGetFullDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //GET MOVIES BY TITLE
    @GetMapping(params="title")
    public ResponseEntity<Set<Movie>> findByTitle(@RequestParam("title") String title) {

        Set<Movie> movies = movieService.findByTitle(title);

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(movies, HttpStatus.OK);

        }
    }

    //GET MOVIES BY GENRE
    @GetMapping(params="genre")
    public ResponseEntity<Set<Movie>> getByGenreId(@RequestParam("genre") Long genreId) {

        Set<Movie> movies = movieService.getByGenreId(genreId);

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(movies, HttpStatus.OK);

        }

    }

    //DELETES A MOVIE
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        try {

            movieService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //SAVES A MOVIE
    @PostMapping()
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {

        try {

            Movie movieCreated = movieService.save(movie);
            return new ResponseEntity<>(movieCreated, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    //UPDATE A MOVIE
    @PatchMapping("/{id}")
    public ResponseEntity<Movie> update(@RequestBody MoviePostDto movie, @PathVariable("id") Long id){

        Optional<Movie> movieToUpdate = movieService.findById(id);

        if (movieToUpdate.isPresent()) {

            Movie movieUpdated = movieService.update(movie, movieToUpdate.get());
            return new ResponseEntity<>(movieUpdated, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }
}
