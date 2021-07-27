package com.alkemy.disneyapi.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoviePostDto {

    private String title;

    private String image;

    private String creationDate;

    private Integer rating;

}
