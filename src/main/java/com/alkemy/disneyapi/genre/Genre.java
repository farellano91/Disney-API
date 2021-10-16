package com.alkemy.disneyapi.genre;

import com.alkemy.disneyapi.movie.Movie;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies = new HashSet<>();

}
