package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.dtos.CharacterGetLiteDto;
import com.alkemy.disneyapi.dtos.CharacterPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) {

        this.characterRepository = characterRepository;

    }

    public Set<CharacterGetLiteDto> getAll() {

        return characterRepository.findAll().stream().map(CharacterGetLiteDto::new).collect(Collectors.toSet());

    }

    public Optional<Character> findById(Long characterId) {

        return characterRepository.findById(characterId);

    }
    public Set<Character> findByName(String name) {

        return characterRepository.findByName(name);

    }
    public Set<Character> findByAge(Integer age) {

        return characterRepository.findByAge(age);

    }

    public void deleteById(Long id){

        characterRepository.deleteById(id);

    }

    public Character save(Character character) {

        return characterRepository.save(character);

    }

    public Set<Character> getByMovieId(Long idMovie) {

        return characterRepository.findAll().stream().filter(x -> isInMovie(x, idMovie)).collect(Collectors.toSet());

    }

    private boolean isInMovie(Character character, Long idMovie) {

        return character.getMovies().stream().anyMatch(m -> m.getId().equals(idMovie));

    }

    public Character update(CharacterPostDto character, Character characterToUpdate) {

        characterToUpdate.setName(character.getName());
        characterToUpdate.setImage(character.getImage());
        characterToUpdate.setAge(character.getAge());
        characterToUpdate.setWeight(character.getWeight());
        characterToUpdate.setHistory(character.getHistory());

        return characterRepository.save(characterToUpdate);

    }

}
