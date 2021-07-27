package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.movie.Movie;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private int age;

    private Float weight;

    private String history;

    @JsonManagedReference
    @JoinTable(name = "Character_Movie",
            joinColumns = @JoinColumn(name = "Character_id"),
            inverseJoinColumns = @JoinColumn(name = "Movie_id"))
    @ManyToMany
    private Set<Movie> movies = new HashSet<Movie>();
}
