package com.example.demo.dtos;

import com.example.demo.enums.RelationEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddRelationDto {

    @Pattern(regexp = "^[A-Za-z\\s]+$")
    @NotBlank
    private String firstWord;

    @Pattern(regexp = "^[A-Za-z\\s]+$")
    @NotBlank
    private String secondWord;

    private RelationEnum relation;
}
