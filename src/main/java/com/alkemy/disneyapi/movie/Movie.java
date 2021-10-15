package com.alkemy.disneyapi.movie;

import com.alkemy.disneyapi.character.Character;
import com.alkemy.disneyapi.genre.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String image;

    private LocalDateTime creationDate;

    private Integer rating;

    @ManyToMany(mappedBy = "movies")
    private Set<Character> characters = new HashSet<>();

    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ManyToMany
    private Set<Genre> genres = new HashSet<>();

}
