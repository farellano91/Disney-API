package com.alkemy.disneyapi.dtos;

import com.alkemy.disneyapi.character.Character;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterGetLiteDto {
    private Long id;
    private String image;
    private String name;

    public CharacterGetLiteDto(Character character) {
        this.setId(character.getId());
        this.setImage(character.getImage());
        this.setName(character.getName());
    }
}
