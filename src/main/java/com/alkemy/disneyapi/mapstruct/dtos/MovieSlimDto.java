package com.alkemy.disneyapi.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSlimDto {
    private Long id;
    private String image;
    private String title;
    private String creationDate;

}
