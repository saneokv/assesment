package com.example.demo.repositories;

import com.example.demo.entities.Relation;
import com.example.demo.enums.RelationEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Integer> {

    Optional<Relation> findByFirstWord_WordAndSecondWord_Word(String firstWord, String secondWord);

    List<Relation> findByRelation(RelationEnum relationEnum);
}
