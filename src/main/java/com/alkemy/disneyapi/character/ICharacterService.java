package com.alkemy.disneyapi.character;

import java.util.List;

public interface ICharacterService {

    List<Character> getAll();

    Character findById(Long characterId);

    List<Character> findByName(String name);

    List<Character> findByAge(Integer age);

    void delete(Long id);

    Character save(Character character);

    List<Character> findByMovieId(Long idMovie);

    boolean checkMoviesExistence(List<Long> moviesIds);

    void addMovies(Long characterId, List<Long> moviesIds);

    void removeMovies(Long characterId, List<Long> moviesIds);

}
