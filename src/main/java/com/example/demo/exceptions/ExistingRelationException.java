package com.example.demo.exceptions;

import com.example.demo.dtos.AddRelationDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExistingRelationException extends RuntimeException {
  private AddRelationDto addRelationDto;

  public ExistingRelationException(String message, AddRelationDto addRelationDto) {
    super(message);
    this.addRelationDto = addRelationDto;
  }

}
