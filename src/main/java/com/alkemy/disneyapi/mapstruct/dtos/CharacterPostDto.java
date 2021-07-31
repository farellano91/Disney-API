package com.alkemy.disneyapi.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CharacterPostDto {

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotNull
    @Min(0)
    private int age;

    @NotNull
    private Float weight;

    @NotBlank
    private String history;

}

