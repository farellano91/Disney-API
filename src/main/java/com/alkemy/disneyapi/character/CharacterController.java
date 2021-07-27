package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.dtos.CharacterGetLiteDto;
import com.alkemy.disneyapi.dtos.CharacterPostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    //GETS ALL CHARACTERS DTO'S
    @GetMapping()
    public ResponseEntity<Set<CharacterGetLiteDto>> getAll() {

         Set<CharacterGetLiteDto> characters = characterService.getAll();

         if(characters.isEmpty()){

             return new ResponseEntity<>(HttpStatus.NO_CONTENT);

         } else {

             return new ResponseEntity<>(characters, HttpStatus.OK);

         }
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<Character> getCharacterDetails(@PathVariable("id") Long id) {

        Optional<Character> character = characterService.findById(id);

        return character.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    //GET CHARACTERS BY NAME
    @GetMapping(params="name")
    public ResponseEntity<Set<Character>> findByName(@RequestParam("name") String name) {

        Set<Character> characters = characterService.findByName(name);

        if(characters.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(characters, HttpStatus.OK);

        }
    }

    //GET CHARACTERS BY AGE
    @GetMapping(params="age")
    public ResponseEntity<Set<Character>> findByAge(@RequestParam("age") Integer age) {

        Set<Character> characters = characterService.findByAge(age);

        if(characters.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(characters, HttpStatus.OK);

        }

    }

    //GET CHARACTERS BY MOVIE
    @GetMapping(params="movie")
    public ResponseEntity<Set<Character>> getByMovieId(@RequestParam("movie") Long movieId) {

        Set<Character> characters = characterService.getByMovieId(movieId);

        if(characters.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {

            return new ResponseEntity<>(characters, HttpStatus.OK);

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
    public ResponseEntity<Character> save(@RequestBody Character character) {

        try {

            Character characterCreated = characterService.save(character);
            return new ResponseEntity<>(characterCreated, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    //UPDATE A CHARACTER
    @PatchMapping("/{id}")
    public ResponseEntity<Character> update(@RequestBody CharacterPostDto character, @PathVariable("id") Long id) {

        Optional<Character> characterToUpdate = characterService.findById(id);

        if (characterToUpdate.isPresent()) {

            Character characterUpdated = characterService.update(character, characterToUpdate.get());
            return new ResponseEntity<>(characterUpdated, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

}
