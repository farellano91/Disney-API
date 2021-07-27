package com.alkemy.disneyapi.mapstruct.mappers;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.mapstruct.dtos.*;
import com.alkemy.disneyapi.movie.Movie;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CharacterSlimDto characterToCharacterSlimDto(Character character);

    CharacterDto characterToCharacterDto(Character character);

    Set<CharacterSlimDto> charactersToCharacterSlimDtos(List<Character> characters);

    Set<CharacterDto> charactersToCharacterDtos(Set<Character> characters);

    Character characterPostDtoToCharacter(CharacterPostDto character);

    MovieSlimDto movieToMovieSlimDto(Movie movie);

    MovieDto movieToMovieDto(Movie movie);

    Set<MovieSlimDto> moviesToMovieSlimDtos(List<Movie> movies);

    Set<MovieDto> moviesToMovieDtos(Set<Movie> movies);

    Movie moviePostDtoToMovie(MoviePostDto movie);

}
