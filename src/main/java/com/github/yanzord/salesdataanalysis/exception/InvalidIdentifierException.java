package com.github.yanzord.salesdataanalysis.exception;

import com.github.yanzord.salesdataanalysis.service.LineIdentifier;

import java.util.Arrays;

public class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException() {
        super("Invalid identifier, expected: " + Arrays.toString(LineIdentifier.values()));
    }
}
