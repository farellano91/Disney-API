package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.movie.Movie;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String image;

    @NotNull
    @Min(0)
    private Integer age;

    @NotNull
    private Float weight;

    @NotBlank
    private String history;

    @JoinTable(name = "Character_Movie",
            joinColumns = @JoinColumn(name = "Character_id"),
            inverseJoinColumns = @JoinColumn(name = "Movie_id"))
    @ManyToMany
    private Set<Movie> movies = new HashSet<>();

}
