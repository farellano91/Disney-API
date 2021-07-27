package com.alkemy.disneyapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterPostDto {

    private String name;

    private String image;

    private int age;

    private Float weight;

    private String history;
}

