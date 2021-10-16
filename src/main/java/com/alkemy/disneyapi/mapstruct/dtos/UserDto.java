package com.alkemy.disneyapi.mapstruct.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    @Schema(example = "peter_griffin@gmail.com", required = true)
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email must have a valid format")
    private String email;

    @Schema(example = "TheBirdIsTheWord", required = true)
    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    private String password;

}
