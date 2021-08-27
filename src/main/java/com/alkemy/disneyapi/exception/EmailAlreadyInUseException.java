package com.alkemy.disneyapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailAlreadyInUseException extends RuntimeException {

    private String message;

}
