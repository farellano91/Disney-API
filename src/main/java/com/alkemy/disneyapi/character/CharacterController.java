package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.exception.ErrorDetails;
import com.alkemy.disneyapi.exception.ResourceNotFoundException;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterDto;
import com.alkemy.disneyapi.mapstruct.dtos.CharacterSlimDto;
import com.alkemy.disneyapi.mapstruct.dtos.ListOfLongDto;
import com.alkemy.disneyapi.mapstruct.dtos.MovieSlimDto;
import com.alkemy.disneyapi.mapstruct.mappers.MapStructMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@OpenAPIDefinition(info = @Info(title = "Disney API",
        description = "API for exploring the world of Disney",
        version = "1.0",
        contact = @Contact(
                name = "Fernando Arellano",
                email = "f.arellano919@gmail.com",
                url = "https://github.com/farellano91"
        ),
        license = @License(
                name = "MIT Licence",
                url = "https://opensource.org/licenses/mit-license.php"
        )
))

@Tag(name = "Characters")
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

    @Operation(description = "Gets all characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All characters are shown",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterSlimDto.class)) }),
            @ApiResponse(responseCode = "204", description = "No characters to show", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<CharacterSlimDto>> getAllCharacters() {

         List<Character> characters = characterService.getAll();

         if(characters.isEmpty()){

             return new ResponseEntity<>(HttpStatus.NO_CONTENT);

         } else {

             return new ResponseEntity<>(mapStructMapper.charactersToCharacterSlimDtos(characters), HttpStatus.OK);

         }

    }

    @Operation(description = "Finds a character by his ID and shows his details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character found",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No character have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacterById(@PathVariable("id") Long id) {

        Optional<Character> character = characterService.findById(id);

        return character.map(value -> new ResponseEntity<>
                 (mapStructMapper.characterToCharacterDto(value), HttpStatus.OK))
                 .orElseThrow(() -> new ResourceNotFoundException("No Character with ID : " + id));

    }

    @GetMapping(params = "name")
    public ResponseEntity<List<CharacterDto>> findCharacterByName(@Parameter(description = "Filter by name") @RequestParam(value = "name", required = false) String name) {

        List<Character> characters = characterService.findByName(name);

        if(characters.isEmpty()){

            throw new ResourceNotFoundException("No Characters with Name : " + name);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    @GetMapping(params="age")
    public ResponseEntity<List<CharacterDto>> findCharacterByAge(@Parameter(description = "Filter by age") @RequestParam(value = "age", required = false) Integer age) {

        List<Character> characters = characterService.findByAge(age);

        if(characters.isEmpty()){

            throw new ResourceNotFoundException("No Characters with Age : " + age);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    @GetMapping(params="movie")
    public ResponseEntity<List<CharacterDto>> findCharacterByMovieId(@Parameter(description = "Filter by MovieID") @RequestParam(value = "movie", required = false) Long movieId) {

        List<Character> characters = characterService.getByMovieId(movieId);

        if(characters.isEmpty()){

            throw new ResourceNotFoundException("No Characters with MovieId : " + movieId);

        } else {

            return new ResponseEntity<>(mapStructMapper.charactersToCharacterDtos(characters), HttpStatus.OK);

        }

    }

    @Operation(description = "Deletes a character by his ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "No character with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCharacterById(@PathVariable("id") Long id) {

        Optional<Character> character = characterService.findById(id);

        if (character.isPresent()) {

            characterService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No Character with ID : " + id);

        }

    }

    @Operation(description = "Saves a character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Character created",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDto.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @PostMapping()
    public ResponseEntity<CharacterDto> saveCharacter(@Validated @RequestBody CharacterDto character) {

        Character characterCreated = characterService.save(mapStructMapper.characterDtoToCharacter(character));
        return new ResponseEntity<>(mapStructMapper.characterToCharacterDto(characterCreated), HttpStatus.CREATED);

    }

    @Operation(description = "Updates a character's info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No character with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CharacterDto> updateCharacter(@Validated @RequestBody CharacterDto character, @PathVariable("id") Long id) {

        Optional<Character> characterToUpdate = characterService.findById(id);

        if (characterToUpdate.isPresent()) {

            Character characterUpdated = characterService.save(mapStructMapper.updateCharacterFromDto(character, characterToUpdate.get()));
            return new ResponseEntity<>(mapStructMapper.characterToCharacterDto(characterUpdated), HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No Character with ID : " + id);

        }

    }

    @Operation(description = "Shows all the movies of the character with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies of the character are shown", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CharacterSlimDto.class)) }),
            @ApiResponse(responseCode = "404", description = "No character with the given ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) } )
    })
    @GetMapping("{id}/movies")
    public ResponseEntity<List<MovieSlimDto>> getCharacterMovies(@PathVariable("id") Long characterId) {

        Optional<Character> character = characterService.findById(characterId);

        if (character.isPresent()) {

            return new ResponseEntity<>(mapStructMapper.moviesToMovieSlimDtos(new ArrayList<>(character.get().getMovies())), HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No character with ID: " + characterId);

        }

    }

    @Operation(description = "Given a list of MovieID's, adds all the corresponding movies to the character's movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies added", content = @Content),
            @ApiResponse(responseCode = "404", description = "No character with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @PutMapping("{id}/movies")
    public ResponseEntity<?> addMoviesToCharacter(@Validated @RequestBody ListOfLongDto moviesIds, @PathVariable("id") Long characterId) {

        Optional<Character> character = characterService.findById(characterId);

        if (character.isPresent()) {

            if (characterService.checkMoviesExistence(moviesIds.getList())) {

                characterService.addMovies(characterId, moviesIds.getList());
                return new ResponseEntity<>(HttpStatus.OK);

            } else {

                throw new ResourceNotFoundException("Make sure all movies you want to add to the character already exist on the server");

            }

        } else {

            throw new ResourceNotFoundException("No character with ID: " + characterId);

        }

    }

    @Operation(description = "Given a list of Movie ID's, removes all the corresponding movies from the character's movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies removed", content = @Content),
            @ApiResponse(responseCode = "404", description = "No character with that ID have been found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) }),
            @ApiResponse(responseCode = "400", description = "There have been validation errors", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class)) })
    })
    @DeleteMapping("{id}/movies")
    public ResponseEntity<?> removeMoviesFromCharacter(@Validated @RequestBody ListOfLongDto moviesIds, @PathVariable("id") Long characterId) {

        Optional<Character> character = characterService.findById(characterId);

        if (character.isPresent()) {

            characterService.removeMovies(characterId, moviesIds.getList());
            return new ResponseEntity<>(HttpStatus.OK);

        } else {

            throw new ResourceNotFoundException("No character with ID: " + characterId);

        }

    }

}
