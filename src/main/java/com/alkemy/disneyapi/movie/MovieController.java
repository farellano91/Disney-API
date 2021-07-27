package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.mapstruct.dtos.MovieDto;
import com.alkemy.disneyapi.mapstruct.dtos.MovieSlimDto;
import com.alkemy.disneyapi.mapstruct.dtos.MoviePostDto;
import com.alkemy.disneyapi.mapstruct.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MapStructMapper mapStructMapper;
    private MovieService movieService;

    @Autowired
    public MovieController(MapStructMapper mapStructMapper, MovieService movieService) {

        this.mapStructMapper = mapStructMapper;
        this.movieService = movieService;

    }

    @GetMapping()
    public ResponseEntity<List<MovieSlimDto>> getAll() {

        List<Movie> movies = movieService.getAll();

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieSlimDtos(movies), HttpStatus.OK);

        }

    }

    //GET MOVIES ORDER BY CREATION_DATE
    @GetMapping(params="order")
    public ResponseEntity<List<MovieDto>> getByGenreId(@RequestParam("order") String order) {

        List<Movie> movies = movieService.findAllOrderByCreationDate(order);

        if(movies == null) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieDtos(movies), HttpStatus.OK);

        }

    }

    //GET MOVIE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findById(@PathVariable("id") Long movieId) {

        Optional<Movie> movie = movieService.findById(movieId);

        return movie.map(value -> new ResponseEntity<>(mapStructMapper.movieToMovieDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //GET MOVIES BY TITLE
    @GetMapping(params="title")
    public ResponseEntity<List<MovieDto>> findByTitle(@RequestParam("title") String title) {

        List<Movie> movies = movieService.findByTitle(title);

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieDtos(movies), HttpStatus.OK);

        }

    }

    //GET MOVIES BY GENRE
    @GetMapping(params="genre")
    public ResponseEntity<List<MovieDto>> getByGenreId(@RequestParam("genre") Long genreId) {

        List<Movie> movies = movieService.getByGenreId(genreId);

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieDtos(movies), HttpStatus.OK);

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
    public ResponseEntity<MovieDto> save(@RequestBody MoviePostDto movie) {

        try {

            Movie movieCreated = movieService.save(mapStructMapper.moviePostDtoToMovie(movie));
            return new ResponseEntity<>(mapStructMapper.movieToMovieDto(movieCreated), HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    //UPDATE A MOVIE
    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> update(@RequestBody MoviePostDto movie, @PathVariable("id") Long id){

        Optional<Movie> movieToUpdate = movieService.findById(id);

        if (movieToUpdate.isPresent()) {

            Movie movieToBeUpdated = mapStructMapper.moviePostDtoToMovie(movie);
            movieToBeUpdated.setId(id);
            Movie movieUpdated = movieService.save(movieToBeUpdated);
            return new ResponseEntity<>(mapStructMapper.movieToMovieDto(movieUpdated), HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

}
