package com.alkemy.disneyapi.mapstruct.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class CharacterDto {

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Mickey", required = true)
    @NotBlank(message = "Name can't be blank")
    private String name;

    @Schema(example = "https://server.com/mickey_image.jpg", required = true)
    @NotBlank(message = "ImageURL can't be blank")
    private String image;

    @Schema(example = "2", required = true)
    @NotNull
    @Min(value = 0, message = "Age must be greater or equal to 0")
    private Integer age;

    @Schema(example = "22.5", required = true)
    @NotNull
    @Min(value = 0, message = "Weight must be greater or equal to 0")
    private Float weight;

    @Schema(example = "Mickey history is an odd one", required = true)
    @NotBlank(message = "History can't be blank")
    private String history;

    @JsonProperty(access= JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MovieSlimDto> movies;

}
