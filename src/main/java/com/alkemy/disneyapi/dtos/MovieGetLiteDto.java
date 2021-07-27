package com.alkemy.disneyapi.dtos;

import com.alkemy.disneyapi.movie.Movie;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieGetLiteDto {
    private String image;
    private String title;
    private String creationDate;

    public MovieGetLiteDto(Movie movie) {
        this.setImage(movie.getImage());
        this.setTitle(movie.getTitle());
        this.setCreationDate(movie.getCreationDate());
    }
}
