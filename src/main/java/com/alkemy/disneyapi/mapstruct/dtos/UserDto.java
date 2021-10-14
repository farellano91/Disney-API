package com.alkemy.disneyapi.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email must have a valid format")
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be at least 8 symbols long")
    private String password;

}
