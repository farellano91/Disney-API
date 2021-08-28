package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.movie.Movie;
import com.alkemy.disneyapi.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class CharacterService implements ICharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    @Override
    public List<Character> getAllCharacters() {

        return characterRepository.findAll();

    }

    @Override
    public Optional<Character> findById(Long characterId) {

        return characterRepository.findById(characterId);

    }
    @Override
    public List<Character> findByName(String name) {

        return characterRepository.findByName(name);

    }
    @Override
    public List<Character> findByAge(Integer age) {

        return characterRepository.findByAge(age);

    }

    @Override
    public void deleteById(Long id){

        characterRepository.deleteById(id);

    }

    @Override
    public Character save(Character character) {

        return characterRepository.save(character);

    }

    @Override
    public List<Character> findByMovieId(Long idMovie) {

        return characterRepository.findByMovieId(idMovie);

    }

    @Override
    public boolean checkMoviesExistence(List<Long> moviesIds) {

        return movieRepository.findAll().stream().map(Movie::getId).collect(Collectors.toList()).containsAll(moviesIds);

    }

    @Override
    public void addMovies(Long characterId, List<Long> moviesIds) {

        Character character = characterRepository.getById(characterId);

        movieRepository.findAllById(moviesIds).forEach(movie -> character.getMovies().add(movie));

        characterRepository.save(character);

    }

    @Override
    public void removeMovies(Long characterId, List<Long> moviesIds) {

        Character character = characterRepository.getById(characterId);

        character.getMovies().removeIf(movie -> moviesIds.contains(movie.getId()));

        characterRepository.save(character);

    }

}
