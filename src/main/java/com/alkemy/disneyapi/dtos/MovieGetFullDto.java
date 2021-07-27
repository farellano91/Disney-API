package com.alkemy.disneyapi.dtos;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.genre.Genre;
import com.alkemy.disneyapi.movie.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieGetFullDto {

    private Long id;

    private String title;

    private String image;

    private String creationDate;

    private Integer rating;

    private Set<Character> characters;

    private Set<Genre> genres;

    public MovieGetFullDto(Movie movie) {
        this.setId(movie.getId());
        this.setTitle(movie.getTitle());
        this.setImage(movie.getImage());
        this.setCreationDate(movie.getCreationDate());
        this.setRating(movie.getRating());
        this.setCharacters(movie.getCharacters());
        this.setGenres(movie.getGenres());
    }
}
