package com.alkemy.disneyapi.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class MoviePostDto {

    private String title;

    private String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date creationDate;

    private Integer rating;

}
