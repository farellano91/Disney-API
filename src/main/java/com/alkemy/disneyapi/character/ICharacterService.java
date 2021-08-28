package com.alkemy.disneyapi.character;

import java.util.List;
import java.util.Optional;

public interface ICharacterService {

    List<Character> getAllCharacters();

    Optional<Character> findById(Long characterId);

    List<Character> findByName(String name);

    List<Character> findByAge(Integer age);

    void deleteById(Long id);

    Character save(Character character);

    List<Character> findByMovieId(Long idMovie);

    boolean checkMoviesExistence(List<Long> moviesIds);

    void addMovies(Long characterId, List<Long> moviesIds);

    void removeMovies(Long characterId, List<Long> moviesIds);

}
