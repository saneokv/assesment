package com.example.demo.services;

import com.example.demo.dtos.AddRelationDto;
import com.example.demo.dtos.RelationDto;
import com.example.demo.entities.Relation;
import com.example.demo.entities.Word;
import com.example.demo.enums.RelationEnum;
import com.example.demo.exceptions.ExistingRelationException;
import com.example.demo.exceptions.WordNotExistsException;
import com.example.demo.repositories.RelationRepository;
import com.example.demo.repositories.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final WordRepository wordRepository;

    private final RelationRepository relationRepository;

    public List<Relation> getRelations(RelationEnum relation) {
        if (relation == null) {
            return relationRepository.findAll();
        } else {
            return relationRepository.findByRelation(relation);
        }
    }

    public List<RelationDto> getInversedRelations() {
        List<Relation> relations = relationRepository.findAll();
        List<RelationDto> relationDtos = new ArrayList<>();
        relationDtos.addAll(relations.stream().map(r -> RelationDto.builder()
                .firstWord(r.getFirstWord().getWord())
                .secondWord(r.getSecondWord().getWord())
                .relation(r.getRelation())
                .inverse("no")
                .build()).toList());
        relationDtos.addAll(relations.stream().map(r -> RelationDto.builder()
                .firstWord(r.getSecondWord().getWord())
                .secondWord(r.getFirstWord().getWord())
                .relation(r.getRelation())
                .inverse("yes")
                .build()).toList());
        return relationDtos;
    }

    @Transactional
    public void addRelation(AddRelationDto addRelationDto) {
        Optional<Relation> relationOptional = relationRepository.findByFirstWord_WordAndSecondWord_Word(addRelationDto.getFirstWord().toLowerCase().trim(), addRelationDto.getSecondWord().toLowerCase().trim());
        Optional<Relation> reverseRelationOptional = relationRepository.findByFirstWord_WordAndSecondWord_Word(addRelationDto.getSecondWord().toLowerCase().trim(), addRelationDto.getFirstWord().toLowerCase().trim());
        if (relationOptional.isPresent() || reverseRelationOptional.isPresent()) {
            throw new ExistingRelationException("The ralation already exists", addRelationDto);
        }

        Word firstWord = wordRepository.findByWord(addRelationDto.getFirstWord().toLowerCase().trim())
                .orElseGet(() -> wordRepository.save(
                        Word.builder().word(addRelationDto.getFirstWord().toLowerCase().trim()).build()));

        Word secondWord = wordRepository.findByWord(addRelationDto.getSecondWord().toLowerCase().trim())
                .orElseGet(() -> wordRepository.save(
                        Word.builder().word(addRelationDto.getSecondWord().toLowerCase().trim()).build()));

        Relation relation = Relation.builder().firstWord(firstWord).secondWord(secondWord).relation(addRelationDto.getRelation()).build();
        relationRepository.save(relation);
    }

    public String getPath(String source, String target) {
        Word sourceWord = wordRepository.findByWord(source.toLowerCase().trim())
                .orElseThrow(() -> new WordNotExistsException("Word does not exist", source));

        Word targetWord = wordRepository.findByWord(target.toLowerCase().trim())
                .orElseThrow(() -> new WordNotExistsException("Word does not exist", target));

        List<Relation> relations = relationRepository.findAll();

        String path = sourceWord.getWord();
        List<Relation> filteredRelations = new ArrayList<>();
        filteredRelations.addAll(relations.stream().filter(r -> r.getFirstWord().getWord().equals(sourceWord.getWord())).toList());
        filteredRelations.addAll(relations.stream().filter(r -> r.getSecondWord().getWord().equals(sourceWord.getWord())).map(r -> Relation.builder().firstWord(r.getSecondWord()).secondWord(r.getFirstWord()).relation(r.getRelation()).build()).toList());

        return null;
    }
}
