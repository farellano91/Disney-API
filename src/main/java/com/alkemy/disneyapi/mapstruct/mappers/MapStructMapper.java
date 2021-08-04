package com.alkemy.disneyapi.mapstruct.mappers;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterDto;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterSlimDto;
import com.alkemy.disneyapi.mapstruct.dtos.MovieDto;
import com.alkemy.disneyapi.mapstruct.dtos.MovieSlimDto;
import com.alkemy.disneyapi.movie.Movie;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    CharacterSlimDto characterToCharacterSlimDto(Character character);

    CharacterDto characterToCharacterDto(Character character);

    Character characterDtoToCharacter(CharacterDto character);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
                 nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Character updateCharacterFromDto(CharacterDto characterDto, @MappingTarget Character character);

    List<CharacterSlimDto> charactersToCharacterSlimDtos(List<Character> characters);

    List<CharacterDto> charactersToCharacterDtos(List<Character> characters);

    MovieSlimDto movieToMovieSlimDto(Movie movie);

    MovieDto movieToMovieDto(Movie movie);

    List<MovieSlimDto> moviesToMovieSlimDtos(List<Movie> movies);

    List<MovieDto> moviesToMovieDtos(List<Movie> movies);

    Movie movieDtoToMovie(MovieDto movie);

}
