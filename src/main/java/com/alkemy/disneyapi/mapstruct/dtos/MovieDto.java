package com.alkemy.disneyapi.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MovieDto {

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Cinderella", required = true)
    @NotBlank(message = "Title can't be blank")
    private String title;

    @Schema(example = "https://server.com/cinderella_image.jpg", required = true)
    @NotBlank(message = "Image can't be blank")
    private String image;

    @NotNull(message = "Date can't be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;

    @Schema(example = "5", required = true)
    @NotNull(message = "Rating can't be null")
    @Min(value = 1, message = "Rating must be greater or equal to 1")
    @Max(value = 5, message = "Rating must be less or equal to 5")
    private Integer rating;

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CharacterSlimDto> characters;

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GenreSlimDto> genres;

}
