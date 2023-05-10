package com.example.demo.controllers;

import com.example.demo.dtos.AddRelationDto;
import com.example.demo.dtos.RelationDto;
import com.example.demo.entities.Relation;
import com.example.demo.enums.RelationEnum;
import com.example.demo.exceptions.ExistingRelationException;
import com.example.demo.services.RelationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/relations")
@RequiredArgsConstructor
public class RelationController {

    private final RelationService relationService;

    @GetMapping
    public List<Relation> getRelations(@RequestParam(value = "relation", required = false) RelationEnum relation) {
        return relationService.getRelations(relation);
    }

    @GetMapping("/inversed")
    public List<RelationDto> getRelationsWithInversed() {
        return relationService.getInversedRelations();
    }

    @GetMapping("/path")
    public String getPath(@RequestParam String source, @RequestParam String target) {
        return null;
    }

    @PostMapping
    public void addRelation(@RequestBody @Valid AddRelationDto addRelationDto) {
        relationService.addRelation(addRelationDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistingRelationException.class)
    public Map<String, String> handleExistingRelationException(
            ExistingRelationException ex) {
        Map<String, String> errors = new HashMap<>();

        errors.put("error", "Relation already exist between " +
                ex.getAddRelationDto().getFirstWord() + " and " + ex.getAddRelationDto().getSecondWord());

        return errors;
    }
}
