package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.exception.ErrorDetails;
import com.alkemy.disneyapi.exception.ResourceNotFoundException;
import com.alkemy.disneyapi.mapstruct.dtos.GenreSlimDto;
import com.alkemy.disneyapi.mapstruct.dtos.ListOfLongDto;
import com.alkemy.disneyapi.mapstruct.dtos.MovieDto;
import com.alkemy.disneyapi.mapstruct.dtos.MovieSlimDto;
import com.alkemy.disneyapi.mapstruct.mappers.MapStructMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Tag(name = "Movies")
@RestController
@RequestMapping("/movies")
@SecurityRequirement(name = "bearerAuth")
public class MovieController {

    private final MapStructMapper mapStructMapper;
    private final MovieService movieService;

    @Operation(description = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies are shown",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieSlimDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No movies to show", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<MovieSlimDto>> getAllMovies() {

        List<Movie> movies = movieService.getAllMovies();

        if(movies.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieSlimDtos(movies), HttpStatus.OK);

        }

    }


    @GetMapping(params="order")
    public ResponseEntity<List<MovieDto>> getAllMoviesOrderByCreationDate(
            @Parameter(description = "Get all movies order by creation date (ASC | DESC)")
            @RequestParam(value ="order", required = false) String order) {

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

    @Operation(description = "Find a movie by its ID and shows its details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No movie have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findMovieById(@PathVariable("id") Long movieId) {

        Optional<Movie> movie = movieService.findById(movieId);

        return movie.map(value -> new ResponseEntity<>
                (mapStructMapper.movieToMovieDto(value), HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException("No Movie with ID : " + movieId));

    }

    @GetMapping(params="title")
    public ResponseEntity<List<MovieDto>> findMovieByTitle(
            @Parameter(description = "Filter movies by title") @RequestParam(value = "title", required = false) String title) {

        List<Movie> movies = movieService.findByTitle(title);

        if(movies.isEmpty()){

            throw new ResourceNotFoundException("No Movies with Title : " + title);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieDtos(movies), HttpStatus.OK);

        }

    }

    @GetMapping(params="genre")
    public ResponseEntity<List<MovieDto>> findMovieByGenre(
            @Parameter(description = "Filter movies by genreID") @RequestParam(value = "genre", required = false) Long genreId) {

        List<Movie> movies = movieService.getByGenreId(genreId);

        if(movies.isEmpty()){

            throw new ResourceNotFoundException("No Movies with GenreId : " + genreId);

        } else {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieDtos(movies), HttpStatus.OK);

        }

    }

    @Operation(description = "Delete a movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "No movie with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMovieById(@PathVariable("id") Long id) {

        Optional<Movie> movie = movieService.findById(id);

        if (movie.isPresent()) {

            movieService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            throw new ResourceNotFoundException("No Movie with ID : " + id);

        }

    }

    @Operation(description = "Save a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @PostMapping()
    public ResponseEntity<MovieDto> saveMovie(@Validated @RequestBody MovieDto movie) {

        Movie movieCreated = movieService.save(mapStructMapper.movieDtoToMovie(movie));
        return new ResponseEntity<>(mapStructMapper.movieToMovieDto(movieCreated), HttpStatus.CREATED);

    }

    @Operation(description = "Update a movie's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No movie with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@Validated @RequestBody MovieDto movie, @PathVariable("id") Long id){

        Optional<Movie> movieToUpdate = movieService.findById(id);

        if (movieToUpdate.isPresent()) {

            Movie movieUpdated = movieService.save(mapStructMapper.updateMovieFromDto(movie, movieToUpdate.get()));
            return new ResponseEntity<>(mapStructMapper.movieToMovieDto(movieUpdated), HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No Movie with ID : " + id);

        }

    }

    @Operation(description = "Shows all the genres of the movie with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All genres of the movie are shown", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = GenreSlimDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No movie with the given ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) } )
    })
    @GetMapping("{id}/genres")
    public ResponseEntity<List<GenreSlimDto>> getMovieGenres(@PathVariable("id") Long movieId) {

        Optional<Movie> movie = movieService.findById(movieId);

        if (movie.isPresent()) {

            return new ResponseEntity<>(mapStructMapper.genresToGenreSlimDtos(new ArrayList<>(movie.get().getGenres())), HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No movie with ID: " + movieId);

        }

    }

    @Operation(description = "Given a list of GenreID's, add all the corresponding genres to the movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genres added", content = @Content),
            @ApiResponse(responseCode = "404", description = "No movie with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @PutMapping("{id}/genres")
    public ResponseEntity<?> addGenresToMovie(@Validated @RequestBody ListOfLongDto genresIds, @PathVariable("id") Long movieId) {

        Optional<Movie> movie = movieService.findById(movieId);

        if (movie.isPresent()) {

            if (movieService.checkGenresExistence(genresIds.getList())) {

                movieService.addGenres(movieId, genresIds.getList());
                return new ResponseEntity<>(HttpStatus.OK);

            } else {

                throw new ResourceNotFoundException("Make sure all genres you want to add to the movie already exist on the server");

            }

        } else {

            throw new ResourceNotFoundException("No movie with ID: " + movieId);

        }

    }

    @Operation(description = "Given a list of GenreID's, remove all the corresponding genres from the movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Genres removed", content = @Content),
            @ApiResponse(responseCode = "404", description = "No movie with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @DeleteMapping("{id}/genres")
    public ResponseEntity<?> removeGenresFromMovie(@Validated @RequestBody ListOfLongDto genresIds, @PathVariable("id") Long movieId) {

        Optional<Movie> movie = movieService.findById(movieId);

        if (movie.isPresent()) {

            movieService.removeGenres(movieId, genresIds.getList());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            throw new ResourceNotFoundException("No movie with ID: " + movieId);

        }

    }

}
