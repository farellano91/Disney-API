package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.genre.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String image;

    private String creationDate;

    private Integer rating;

    @JsonBackReference
    @ManyToMany(mappedBy = "movies")
    private Set<Character> characters = new HashSet<Character>();

    @JoinTable(name = "Movie_Genre",
            joinColumns = @JoinColumn(name = "Movie_id"),
            inverseJoinColumns = @JoinColumn(name = "Genre_id"))
    @ManyToMany
    private Set<Genre> genres = new HashSet<Genre>();
}
