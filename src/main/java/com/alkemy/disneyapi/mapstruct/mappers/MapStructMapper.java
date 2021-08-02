package com.alkemy.disneyapi.mapstruct.mappers;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.genre.Genre;
import com.alkemy.disneyapi.mapstruct.dtos.*;
import com.alkemy.disneyapi.movie.Movie;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CharacterSlimDto characterToCharacterSlimDto(Character character);

    CharacterDto characterToCharacterDto(Character character);

    Character characterDtoToCharacter(CharacterDto character);

    List<CharacterSlimDto> charactersToCharacterSlimDtos(List<Character> characters);

    List<CharacterDto> charactersToCharacterDtos(List<Character> characters);

    MovieSlimDto movieToMovieSlimDto(Movie movie);

    MovieDto movieToMovieDto(Movie movie);

    List<MovieSlimDto> moviesToMovieSlimDtos(List<Movie> movies);

    List<MovieDto> moviesToMovieDtos(List<Movie> movies);

    Movie movieDtoToMovie(MovieDto movie);

    List<GenreSlimDto> genresToGenreSlimDtos(List<Genre> genres);
}
