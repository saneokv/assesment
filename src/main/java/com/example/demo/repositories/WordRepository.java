package com.example.demo.repositories;

import com.example.demo.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {

    Optional<Word> findByWord(String word);
}
