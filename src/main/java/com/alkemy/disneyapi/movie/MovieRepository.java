package com.alkemy.disneyapi.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "SELECT m FROM Movie m LEFT OUTER JOIN FETCH m.characters LEFT OUTER JOIN FETCH m.genres WHERE m.id = ?1")
    Optional<Movie> findById(Long id);

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT OUTER JOIN FETCH m.characters LEFT OUTER JOIN FETCH m.genres WHERE m.title = ?1")
    List<Movie> findByTitle(String title);

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT OUTER JOIN FETCH m.characters LEFT OUTER JOIN FETCH m.genres g WHERE g.id = ?1")
    List<Movie> findByGenre(Long genreId);

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT OUTER JOIN FETCH m.characters LEFT OUTER JOIN FETCH m.genres ORDER BY m.creationDate ASC")
    List<Movie> findAllByOrderByCreationDateAsc();

    @Query(value = "SELECT DISTINCT m FROM Movie m LEFT OUTER JOIN FETCH m.characters LEFT OUTER JOIN FETCH m.genres ORDER BY m.creationDate DESC")
    List<Movie> findAllByOrderByCreationDateDesc();

}