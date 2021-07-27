package com.alkemy.disneyapi.mapstruct.dtos;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.genre.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MovieDto {

    private Long id;

    private String title;

    private String image;

    private String creationDate;

    private Integer rating;

    private Set<CharacterSlimDto> characters;

    private Set<Genre> genres;

}
