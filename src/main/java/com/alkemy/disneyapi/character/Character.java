package com.alkemy.disneyapi.character;

import com.alkemy.disneyapi.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private Integer age;

    private Float weight;

    private String history;

    @JoinTable(name = "Character_Movie",
            joinColumns = @JoinColumn(name = "Character_id"),
            inverseJoinColumns = @JoinColumn(name = "Movie_id"))
    @ManyToMany
    private Set<Movie> movies = new HashSet<>();

}
