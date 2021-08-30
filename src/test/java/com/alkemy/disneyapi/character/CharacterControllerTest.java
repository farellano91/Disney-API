package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.exception.GlobalResponseException;
import com.alkemy.disneyapi.exception.ResourceNotFoundException;
import com.alkemy.disneyapi.mapstruct.dtos.ListOfLongDto;
import com.alkemy.disneyapi.mapstruct.mappers.MapStructMapperImpl;
import com.alkemy.disneyapi.movie.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {MapStructMapperImpl.class, GlobalResponseException.class})
@AutoConfigureMockMvc(addFilters = false)
@Import(CharacterController.class)
@WebMvcTest(controllers = CharacterController.class)
class CharacterControllerTest {

    @MockBean
    private CharacterService characterService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Character character1 = new Character(1L, "name1", "image1", 20, 5.1F, "history1", new HashSet<>());
    Character character2 = new Character(2L, "name2", "image2", 21, 5.2F, "history2", new HashSet<>());
    Character characterNotValid = new Character(1L, null, "image1", 20, 5.1F, "history1", new HashSet<>());
    List<Character> listCharacters = Arrays.asList(character1, character2);
    Movie movie1 = new Movie(1L, "title1", "image1", new Date(), 4, new HashSet<>(), new HashSet<>());

    @Test
    void getAllCharacters() throws Exception {

        when(characterService.getAllCharacters()).thenReturn(listCharacters);

        mockMvc.perform(get("/characters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    void getCharacterByIdFound() throws Exception {

        when(characterService.findById(1L)).thenReturn(Optional.of(character1));

        mockMvc.perform(get("/characters/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"));

    }

    @Test
    void getCharacterByIdNotFound() throws Exception {

        mockMvc.perform(get("/characters/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("No Character with ID : 1", result.getResolvedException().getMessage()));

    }

    @Test
    void findCharacterByNameFound() throws Exception {

        when(characterService.findByName("name1")).thenReturn(Collections.singletonList(character1));

        mockMvc.perform(get("/characters?name=name1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("name1"));

    }

    @Test
    void findCharacterByNameNotFound() throws Exception {

        mockMvc.perform(get("/characters?name=name1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("No Characters with name : name1", result.getResolvedException().getMessage()));

    }

    @Test
    void findCharacterByAgeFound() throws Exception {

        when(characterService.findByAge(20)).thenReturn(Collections.singletonList(character1));

        mockMvc.perform(get("/characters?age=20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(20));


    }

    @Test
    void findCharacterByAgeNotFound() throws Exception {

        mockMvc.perform(get("/characters?age=20"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("No Characters with age : 20", result.getResolvedException().getMessage()));

    }

    @Test
    void findCharacterByMovieIdFound() throws Exception {

        character1.getMovies().add(movie1);
        movie1.getCharacters().add(character1);

        when(characterService.findByMovieId(1L)).thenReturn(Collections.singletonList(character1));

        mockMvc.perform(get("/characters?movie=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].movies[0].id").value(1));


    }

    @Test
    void findCharacterByMovieIdNotFound() throws Exception {

        mockMvc.perform(get("/characters?movie=1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("No Characters with movieId : 1", result.getResolvedException().getMessage()));

    }

    @Test
    void deleteCharacterByIdFound() throws Exception {

        when(characterService.findById(1L)).thenReturn(Optional.of(character1));

        mockMvc.perform(delete("/characters/{id}", 1L))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteCharacterByIdNotFound() throws Exception {

        mockMvc.perform(delete("/characters/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("No Character with ID : 1", result.getResolvedException().getMessage()));

    }

    @Test
    void saveCharacterValid() throws Exception {

        when(characterService.save(any(Character.class))).thenReturn(character1);

        mockMvc.perform(post("/characters")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(character1)))
                .andExpect(status().isCreated());

    }

    @Test
    void saveCharacterNotValid() throws Exception {

        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterNotValid)))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }

    @Test
    void updateCharacterValid() throws Exception {

        when(characterService.findById(1L)).thenReturn(Optional.of(character1));
        when(characterService.save(any(Character.class))).thenReturn(character1);

        mockMvc.perform(patch("/characters/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(character1)))
                        .andExpect(status().isOk());
    }

    @Test
    void updateCharacterNotValid() throws Exception {

        mockMvc.perform(patch("/characters/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(characterNotValid)))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    void getCharacterMovies() throws Exception {

        character1.getMovies().add(movie1);
        movie1.getCharacters().add(character1);

        when(characterService.findById(1L)).thenReturn(Optional.of(character1));

        mockMvc.perform(get("/characters/{id}/movies", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title1"));

    }

    @Test
    void addMoviesToCharacterNotValid() throws  Exception {

        mockMvc.perform(put("/characters/{id}/movies", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ListOfLongDto())))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }

    @Test
    void removeMoviesFromCharacterNotValid() throws Exception {

        mockMvc.perform(delete("/characters/{id}/movies", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ListOfLongDto())))
                        .andExpect(status().isBadRequest())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

    }
}