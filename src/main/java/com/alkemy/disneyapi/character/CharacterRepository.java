package com.alkemy.disneyapi.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    Set<Character> findByName(String name);

    Set<Character> findByAge(Integer age);

}