package com.alkemy.disneyapi.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query(value = "SELECT c FROM Character c LEFT OUTER JOIN FETCH c.movies WHERE c.id = ?1")
    Optional<Character> findById(Long id);

    @Query(value = "SELECT DISTINCT c FROM Character c LEFT OUTER JOIN FETCH c.movies WHERE c.name = ?1")
    List<Character> findByName(String name);

    @Query(value = "SELECT DISTINCT c FROM Character c LEFT OUTER JOIN FETCH c.movies WHERE c.age = ?1")
    List<Character> findByAge(Integer age);

    @Query(value = "SELECT DISTINCT c FROM Character c LEFT OUTER JOIN FETCH c.movies m WHERE m.id = ?1")
    List<Character> findByMovieId(Long id);

}