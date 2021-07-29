package com.alkemy.disneyapi.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

    List<Movie> findAllByOrderByCreationDateAsc();

    List<Movie> findAllByOrderByCreationDateDesc();

}