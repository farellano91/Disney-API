package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.mapstruct.dtos.CharacterDto;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterSlimDto;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterPostDto;
import com.alkemy.disneyapi.mapstruct.dtos.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<Set<CharacterSlimDto>> getAll() {

         List<Character> characters = characterService.getAll();

         if(characters.isEmpty()){

             return new ResponseEntity<>(HttpStatus.NO_CONTENT);

         } else {

             return new ResponseEntity<>(mapStructMapper.charactersToCharacterSlimDtos(characters), HttpStatus.OK);

         }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CharacterDto> getCharacterDetails(@PathVariable("id") Long id) {

        Optional<Character> character = characterService.findById(id);

        return character.map(value -> new ResponseEntity<>(mapStructMapper.characterToCharacterDto(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //GET CHARACTERS BY NAME
    @GetMapping(params="name")
    public ResponseEntity<Set<CharacterDto>> findByName(@RequestParam("name") String name) {

        Set<Character> characters = characterService.findByName(name);

        if(characters.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }
    }

    //GET CHARACTERS BY AGE
    @GetMapping(params="age")
    public ResponseEntity<Set<CharacterDto>> findByAge(@RequestParam("age") Integer age) {

        Set<Character> characters = characterService.findByAge(age);

        if(characters.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    //GET CHARACTERS BY MOVIE
    @GetMapping(params="movie")
    public ResponseEntity<Set<CharacterDto>> getByMovieId(@RequestParam("movie") Long movieId) {

        Set<Character> characters = characterService.getByMovieId(movieId);

        if(characters.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    //DELETES A CHARACTER
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {

        try {

            characterService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //SAVES A CHARACTER
    @PostMapping()
    public ResponseEntity<CharacterDto> save(@RequestBody CharacterPostDto character) {

        try {

            Character characterCreated = characterService.save(mapStructMapper.characterPostDtoToCharacter(character));
            return new ResponseEntity<>(mapStructMapper.characterToCharacterDto(characterCreated), HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    //UPDATE A CHARACTER
    @PatchMapping("/{id}")
    public ResponseEntity<CharacterDto> update(@RequestBody CharacterPostDto character, @PathVariable("id") Long id) {

        Optional<Character> characterToUpdate = characterService.findById(id);

        if (characterToUpdate.isPresent()) {

            Character characterToBeUpdated = mapStructMapper.characterPostDtoToCharacter(character);
            characterToBeUpdated.setId(id);
            Character characterUpdated = characterService.save(characterToBeUpdated);
            return new ResponseEntity<>(mapStructMapper.characterToCharacterDto(characterUpdated), HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

}
