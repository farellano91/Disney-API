package com.alkemy.disneyapi.mapstruct.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CharacterMoviesDto {

    private List<Long> movies;

}
