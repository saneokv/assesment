package com.example.demo.exceptions;

import lombok.Data;

@Data
public class PathNotFoundException extends RuntimeException {

    public PathNotFoundException(String message) {
        super(message);
    }
}
