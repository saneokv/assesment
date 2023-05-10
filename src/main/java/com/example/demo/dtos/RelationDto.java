package com.example.demo.dtos;

import com.example.demo.enums.RelationEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationDto {

    private String firstWord;
    private String secondWord;
    private RelationEnum relation;
    private String inverse;
}
