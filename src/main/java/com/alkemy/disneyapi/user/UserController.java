package com.alkemy.disneyapi.user;

import com.alkemy.disneyapi.exception.EmailAlreadyInUseException;
import com.alkemy.disneyapi.mapstruct.dtos.LoginResponse;
import com.alkemy.disneyapi.mapstruct.dtos.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;

    }

    @Operation(description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration succesfull",content = @Content),
            @ApiResponse(responseCode = "400", description = "Email already in use by another user", content = @Content)
    })
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody UserDto user) {

        if(userService.checkEmailExistence(user.getEmail())) {

            throw new EmailAlreadyInUseException(user.getEmail() + " is already in use");

        }

        userService.saveUser(user);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(description = "User login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfull login",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "No user have been found with the given credentials", content = @Content)
    })
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> loginUser(@Validated @RequestBody UserDto user) {

        final String jwt = userService.logInUser(user);

        if (jwt == null) {

            throw new UsernameNotFoundException("Email " + user.getEmail() + " not found");

        } else {

            return new ResponseEntity<>(new LoginResponse(jwt), HttpStatus.OK);

        }

    }

}
