package com.alkemy.disneyapi.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CharacterDto {

    private Long id;

    private String name;

    private String image;

    private int age;

    private Float weight;

    private String history;

    private Set<MovieSlimDto> movies;

}
