package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.movie.Movie;
import com.alkemy.disneyapi.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository, MovieRepository movieRepository) {

        this.characterRepository = characterRepository;

        this.movieRepository = movieRepository;
    }

    public List<Character> getAllCharacters() {

        return characterRepository.findAll();

    }

    public Optional<Character> findById(Long characterId) {

        return characterRepository.findById(characterId);

    }
    public List<Character> findByName(String name) {

        return characterRepository.findByName(name);

    }
    public List<Character> findByAge(Integer age) {

        return characterRepository.findByAge(age);

    }

    public void deleteById(Long id){

        characterRepository.deleteById(id);

    }

    public Character save(Character character) {

        return characterRepository.save(character);

    }

    public List<Character> findByMovieId(Long idMovie) {

        return characterRepository.findByMovieId(idMovie);

    }

    public boolean checkMoviesExistence(List<Long> moviesIds) {

        return movieRepository.findAll().stream().map(Movie::getId).collect(Collectors.toList()).containsAll(moviesIds);

    }

    public void addMovies(Long characterId, List<Long> moviesIds) {

        Character character = characterRepository.getById(characterId);

        movieRepository.findAllById(moviesIds).forEach(movie -> character.getMovies().add(movie));

        characterRepository.save(character);

    }

    public void removeMovies(Long characterId, List<Long> moviesIds) {

        Character character = characterRepository.getById(characterId);

        character.getMovies().removeIf(movie -> moviesIds.contains(movie.getId()));

        characterRepository.save(character);

    }

}
