package com.example.demo.exceptions;

import com.example.demo.dtos.AddRelationDto;
import com.example.demo.entities.Word;
import lombok.Data;

@Data
public class WordNotExistsException extends RuntimeException {

    private String word;

    public WordNotExistsException(String message, String word) {
        super(message);
        this.word = word;
    }
}
