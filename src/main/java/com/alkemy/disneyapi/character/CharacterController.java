package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.exception.ResourceNotFoundException;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterDto;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterPostDto;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterSlimDto;
import com.alkemy.disneyapi.mapstruct.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final MapStructMapper mapStructMapper;
    private final CharacterService characterService;

    @Autowired
    public CharacterController(MapStructMapper mapStructMapper, CharacterService characterService) {

        this.mapStructMapper = mapStructMapper;
        this.characterService = characterService;

    }

    //GETS ALL CHARACTERS DTO'S
    @GetMapping()
    public ResponseEntity<List<CharacterSlimDto>> getAll() {

         List<Character> characters = characterService.getAll();

         if(characters.isEmpty()){

             return new ResponseEntity<>(HttpStatus.NO_CONTENT);

         } else {

             return new ResponseEntity<>(mapStructMapper.charactersToCharacterSlimDtos(characters), HttpStatus.OK);

         }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacterDetails(@PathVariable("id") Long id) {

        Optional<Character> character = characterService.findById(id);

        return character.map(value -> new ResponseEntity<>
                 (mapStructMapper.characterToCharacterDto(value), HttpStatus.OK))
                 .orElseThrow(() -> new ResourceNotFoundException("No Character with ID : " + id));

    }

    //GET CHARACTERS BY NAME
    @GetMapping(params = "name")
    public ResponseEntity<List<CharacterDto>> findByName(@RequestParam(value = "name") String name) {

        List<Character> characters = characterService.findByName(name);

        if(characters.isEmpty()){

            throw new ResourceNotFoundException("No Characters with Name : " + name);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    //GET CHARACTERS BY AGE
    @GetMapping(params="age")
    public ResponseEntity<List<CharacterDto>> findByAge(@RequestParam("age") Integer age) {

        List<Character> characters = characterService.findByAge(age);

        if(characters.isEmpty()){

            throw new ResourceNotFoundException("No Characters with Age : " + age);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    //GET CHARACTERS BY MOVIE
    @GetMapping(params="movie")
    public ResponseEntity<List<CharacterDto>> getByMovieId(@RequestParam("movie") Long movieId) {

        List<Character> characters = characterService.getByMovieId(movieId);

        if(characters.isEmpty()){

            throw new ResourceNotFoundException("No Characters with MovieId : " + movieId);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    //DELETES A CHARACTER
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        Optional<Character> character = characterService.findById(id);

        if (character.isPresent()) {

            characterService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No Character with ID : " + id);

        }

    }

    //SAVES A CHARACTER
    @PostMapping()
    public ResponseEntity<Object> save(@Validated @RequestBody CharacterPostDto character) {

            Character characterCreated = characterService.save(mapStructMapper.characterPostDtoToCharacter(character));
            return new ResponseEntity<>(mapStructMapper.characterToCharacterDto(characterCreated), HttpStatus.CREATED);

    }

    //UPDATE A CHARACTER
    @PatchMapping("/{id}")
    public ResponseEntity<CharacterDto> update(@Validated @RequestBody CharacterPostDto character, @PathVariable("id") Long id) {

        Optional<Character> characterToUpdate = characterService.findById(id);

        if (characterToUpdate.isPresent()) {

            Character characterToBeUpdated = mapStructMapper.characterPostDtoToCharacter(character);
            characterToBeUpdated.setId(id);
            Character characterUpdated = characterService.save(characterToBeUpdated);
            return new ResponseEntity<>(mapStructMapper.characterToCharacterDto(characterUpdated), HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No Character with ID : " + id);

        }

    }

}
